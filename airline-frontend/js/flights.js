document.addEventListener('DOMContentLoaded', async () => {
    requireAuth();
    updateNavbar();
    setupLogout();

    const params   = new URLSearchParams(window.location.search);
    const flightId = params.get('flightId');

    if (flightId) await loadFlightDetails(flightId);

    setupReservationForm();
});

async function loadFlightDetails(id) {
    try {
        const flight = await Flights.getById(id);
        renderFlightDetails(flight);
        document.getElementById('hidden-flight-id').value = id;
    } catch (err) {
        document.getElementById('flight-details').innerHTML =
            `<p class="error-msg">No se pudo cargar el vuelo. ${err.message}</p>`;
    }
}

function renderFlightDetails(f) {
    const el = document.getElementById('flight-details');
    if (!el) return;

    const date = f.departureTime
        ? new Date(f.departureTime).toLocaleDateString('es-SV')
        : 'Por definir';

    el.innerHTML = `
        <p>${f.origin || 'San Salvador (SAL)'} → ${f.destination || 'Destino'}</p>
        <p>Fecha de salida: ${date}</p>
        <p>Asientos disponibles: ${f.availableSeats ?? '—'}</p>
        <p>Aerolínea: ${f.airline?.name || '—'}</p>
        <p>Precio Total: $${f.price || '—'} USD</p>
    `;

    document.getElementById('hidden-flight-id').dataset.price = f.price || 0;
}

function setupReservationForm() {
    const form = document.getElementById('reservation-form');
    if (!form) return;

    const btnAddTrips = document.getElementById('btn-add-trips');
    if (btnAddTrips) {
        btnAddTrips.addEventListener('click', async () => {
            await procesarReserva('Reservado', btnAddTrips);
        });
    }

    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        const btnPay = form.querySelector('button[type="submit"]');
        await procesarReserva('Confirmado', btnPay);
    });
}

async function procesarReserva(nuevoEstado, botonPresionado) {
    clearErrors();

    const firstName = document.getElementById('firstName').value.trim();
    const lastName  = document.getElementById('lastName').value.trim();
    const dob       = document.getElementById('dob').value;
    const passport  = document.getElementById('passport').value.trim();
    const seat      = document.getElementById('seat').value;
    const flightId  = document.getElementById('hidden-flight-id').value;

    let valid = true;
    if (!firstName) { showError('err-firstName', 'El nombre es requerido.');    valid = false; }
    if (!lastName)  { showError('err-lastName',  'El apellido es requerido.');  valid = false; }
    if (!dob)       { showError('err-dob',        'La fecha es requerida.');     valid = false; }
    if (!passport)  { showError('err-passport',   'El pasaporte es requerido.'); valid = false; }
    if (passport && !/^[A-Z0-9]{6,20}$/i.test(passport)) {
        showError('err-passport', 'El pasaporte debe tener entre 6 y 20 caracteres alfanuméricos.');
        valid = false;
    }
    if (!valid) return;

    if (new Date(dob) >= new Date()) {
        showError('err-dob', 'La fecha de nacimiento debe ser en el pasado.');
        return;
    }

    const textoOriginal = botonPresionado.textContent;
    botonPresionado.disabled = true;
    botonPresionado.textContent = 'Guardando...';

    try {
        const passenger = await Passengers.create({
            fullname:  `${firstName} ${lastName}`,
            birthDate: dob,
            passport:  passport.toUpperCase()
        });

        const price = parseFloat(
            document.getElementById('hidden-flight-id').dataset.price || 0
        );

        await Reservations.create({
            seat:      seat,
            totalPrice: price,
            status:    nuevoEstado,
            flight:    { id: parseInt(flightId) },
            passenger: { id: passenger.id }
        });

        showSuccess('¡Viaje guardado con éxito! Redirigiendo a Mis Viajes...');
        setTimeout(() => window.location.href = 'my-trips.html', 1500);

    } catch (err) {
        showGlobalError(err.message || 'Error al procesar la reserva. Intente de nuevo.');
        botonPresionado.disabled = false;
        botonPresionado.textContent = textoOriginal;
    }
}

function showError(id, msg) {
    const el = document.getElementById(id);
    if (el) { el.textContent = msg; el.style.display = 'block'; }
}
function clearErrors() {
    document.querySelectorAll('.field-error').forEach(e => {
        e.textContent = '';
        e.style.display = 'none';
    });
    const ge = document.getElementById('global-error');
    if (ge) ge.style.display = 'none';
}
function showGlobalError(msg) {
    const el = document.getElementById('global-error');
    if (el) { el.textContent = msg; el.style.display = 'block'; }
}
function showSuccess(msg) {
    const el = document.getElementById('success-msg');
    if (el) { el.textContent = msg; el.style.display = 'block'; }
}
function setupLogout() {
    const btn = document.getElementById('btn-logout');
    if (btn) btn.addEventListener('click', logout);
}