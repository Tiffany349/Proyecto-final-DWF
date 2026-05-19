document.addEventListener('DOMContentLoaded', async () => {
    requireAuth();
    updateNavbar();
    setupLogout();
    setupBackBtn();
    await loadMyTrips();
});

async function loadMyTrips() {
    const container = document.getElementById('trips-container');
    if (!container) return;

    container.innerHTML = `<div class="loading-msg" style="color:#0052cc;text-align:center;padding:40px;">
        <i class="fa-solid fa-spinner fa-spin"></i> Cargando tus viajes...</div>`;

    try {
        const reservations = await Reservations.getAll();

        if (!reservations || !reservations.length) {
            container.innerHTML = `
                <div class="empty-state">
                    <i class="fa-solid fa-plane-slash"></i>
                    <p>Aún no tienes viajes reservados.</p>
                    <a href="index.html" class="btn-primary">Explorar vuelos</a>
                </div>`;
            return;
        }

        container.innerHTML = reservations.map(r => buildTripCard(r)).join('');

    } catch (err) {
        container.innerHTML = `<p class="error-msg"><i class="fa-solid fa-circle-exclamation"></i> ${err.message}</p>`;
    }
}

function buildTripCard(r) {
    const flight      = r.flight || {};
    const isCancelled = r.status === 'CANCELLED' || r.status === 'Cancelado';
    const isPaid      = r.status === 'PAID'       || r.status === 'Pagado';

    let formattedDate = 'Por definir';
    if (flight.departureTime) {
        const d = new Date(flight.departureTime);
        formattedDate = `${String(d.getDate()).padStart(2,'0')} / ${String(d.getMonth()+1).padStart(2,'0')} / ${d.getFullYear()}`;
    }

    return `
    <div class="trip-card-custom" id="trip-${r.id}"
         style="background-image:linear-gradient(to right,rgba(0,0,0,0.55) 45%,rgba(0,0,0,0.1)),url('assets/images/destination-1.jpg');">
        <div class="trip-info-left">
            <div class="info-row"><span class="label">Destino:</span> <span class="value">${flight.destination || 'Por definir'}</span></div>
            <div class="info-row"><span class="label">Origen:</span>  <span class="value">${flight.origin      || 'Por definir'}</span></div>
            <div class="info-row"><span class="label">Salida:</span>  <span class="value">${formattedDate}</span></div>
            <div class="info-row"><span class="label">Precio:</span>  <span class="value">$${r.totalPrice || flight.price || '—'} USD</span></div>
            <div class="info-row sub-details">${r.seat || 'Económica'} | ${flight.airline?.name || 'Aerolínea'}</div>
            <div class="info-row sub-details" style="margin-top:4px;">
                Estado: <strong>${getStatusLabel(r.status)}</strong>
            </div>
            ${isCancelled ? `<div style="color:#ff4d4d;font-weight:bold;margin-top:5px;font-size:13px;">
                <i class="fa-solid fa-circle-xmark"></i> Reserva Cancelada</div>` : ''}
        </div>
        <div class="trip-actions-right">
            ${!isPaid && !isCancelled ? `
            <button class="btn-action btn-pay" onclick="openPayModal(${r.id}, ${r.totalPrice || 0})">
                Pagar ahora
            </button>` : ''}
            ${!isCancelled ? `
            <button class="btn-action btn-cancel-custom" onclick="cancelTrip(${r.id})">
                Cancelar Reserva
            </button>` : `
            <button class="btn-action btn-cancel-custom" disabled style="opacity:0.5;cursor:not-allowed;">
                Cancelada
            </button>`}
        </div>
    </div>`;
}

function getStatusLabel(status) {
    const labels = {
        'CONFIRMED': 'Confirmado', 'Confirmado': 'Confirmado',
        'CANCELLED': 'Cancelado',  'Cancelado':  'Cancelado',
        'Reservado': 'Reservado',
        'PAID':      'Pagado',     'Pagado':     'Pagado'
    };
    return labels[status] || status || 'Pendiente';
}

function openPayModal(reservationId, currentPrice) {
    const old = document.getElementById('pay-modal-overlay');
    if (old) old.remove();

    const overlay = document.createElement('div');
    overlay.id = 'pay-modal-overlay';
    overlay.style.cssText = 'position:fixed;inset:0;background:rgba(0,0,0,0.6);display:flex;align-items:center;justify-content:center;z-index:9999;';
    overlay.innerHTML = `
        <div style="background:#fff;border-radius:12px;padding:28px 32px;max-width:400px;width:90%;color:#222;">
            <h3 style="margin:0 0 16px;">Pago de Reserva #${reservationId}</h3>
            <div id="pay-error" style="color:red;margin-bottom:10px;display:none;font-size:13px;"></div>
            <label style="font-size:13px;font-weight:500;">Método de pago</label>
            <select id="pay-method" style="width:100%;padding:8px;margin:6px 0 14px;border-radius:6px;border:1px solid #ccc;">
                <option value="Tarjeta de Crédito">Tarjeta de Crédito</option>
                <option value="Tarjeta de Débito">Tarjeta de Débito</option>
                <option value="PayPal">PayPal</option>
                <option value="Transferencia">Transferencia Bancaria</option>
            </select>
            <label style="font-size:13px;font-weight:500;">Monto (USD)</label>
            <input id="pay-amount" type="number" min="0.01" step="0.01" value="${currentPrice || ''}"
                   style="width:100%;padding:8px;margin:6px 0 18px;border-radius:6px;border:1px solid #ccc;box-sizing:border-box;">
            <div style="display:flex;gap:10px;">
                <button onclick="submitPayment(${reservationId})"
                        style="flex:1;padding:10px;background:#0052cc;color:#fff;border:none;border-radius:8px;cursor:pointer;font-weight:500;">
                    Confirmar Pago
                </button>
                <button onclick="document.getElementById('pay-modal-overlay').remove()"
                        style="flex:1;padding:10px;background:#eee;color:#333;border:none;border-radius:8px;cursor:pointer;">
                    Cancelar
                </button>
            </div>
        </div>`;
    document.body.appendChild(overlay);
}

async function submitPayment(reservationId) {
    const method = document.getElementById('pay-method').value;
    const amount = parseFloat(document.getElementById('pay-amount').value);
    const errEl  = document.getElementById('pay-error');

    if (!amount || amount <= 0) {
        errEl.textContent = 'El monto debe ser mayor a 0.';
        errEl.style.display = 'block';
        return;
    }

    try {
        await Payments.create({
            paymentMethod: method,
            amount:        amount,
            reservation:   { id: reservationId }
        });
        document.getElementById('pay-modal-overlay').remove();
        alert('¡Pago registrado correctamente!');
        await loadMyTrips();
    } catch (err) {
        errEl.textContent = err.message || 'Error al procesar el pago.';
        errEl.style.display = 'block';
    }
}

async function cancelTrip(id) {
    if (!confirm('¿Estás seguro de que deseas cancelar esta reserva?')) return;

    const card = document.getElementById(`trip-${id}`);
    const btn  = card?.querySelector('.btn-cancel-custom');
    if (btn) { btn.disabled = true; btn.textContent = 'Cancelando...'; }

    try {
        await Reservations.cancel(id);
        await loadMyTrips();
    } catch (err) {
        alert('No se pudo cancelar: ' + err.message);
        if (btn) { btn.disabled = false; btn.textContent = 'Cancelar Reserva'; }
    }
}

function setupLogout() {
    const btn = document.getElementById('btn-logout');
    if (btn) btn.addEventListener('click', logout);
}

function setupBackBtn() {
    const backBtn = document.querySelector('.back-btn');
    if (backBtn) backBtn.addEventListener('click', () => window.history.back());
}