document.addEventListener('DOMContentLoaded', () => {
    updateNavbar();
    loadFlights();
    setupSearch();
    setupLogout();
});

async function loadFlights(origin = '', destination = '') {
    const grid = document.getElementById('flights-grid');
    if (!grid) return;

    grid.innerHTML = `<div class="loading-msg"><i class="fa-solid fa-spinner fa-spin"></i> Cargando vuelos...</div>`;

    try {
        const flights = await Flights.getAll();

        let filtered = flights;
        if (origin)      filtered = filtered.filter(f => f.origin?.toLowerCase().includes(origin.toLowerCase()));
        if (destination) filtered = filtered.filter(f => f.destination?.toLowerCase().includes(destination.toLowerCase()));

        if (!filtered.length) {
            grid.innerHTML = `<p class="no-results">No se encontraron vuelos disponibles.</p>`;
            return;
        }

        grid.innerHTML = filtered.map((f, i) => buildFlightCard(f, i)).join('');

    } catch (err) {
        grid.innerHTML = staticCards();
    }
}

function buildFlightCard(f, index) {
    const imgs = [
        'assets/images/destination-1.jpg',
        'assets/images/destination-2.jpg',
        'assets/images/destination-3.jpg',
        'assets/images/destination-4.jpg'
    ];
    const img  = imgs[index % imgs.length];
    const date = f.departureTime ? new Date(f.departureTime).toLocaleDateString('es-SV') : 'Por definir';

    return `
    <div class="card">
        <img src="${img}" alt="${f.destination}">
        <div class="card-content">
            <h3>SAL → ${f.destination || 'Destino'}</h3>
            <p>Salida ${date}</p>
            <span class="price">$${f.price || '—'} USD</span>
            <button onclick="goToReserve(${f.id})">Reservar ahora</button>
        </div>
    </div>`;
}

function staticCards() {
    const data = [
        { dest: 'Los Ángeles (LAX)', date: '16/05/2026', price: 136, img: 'destination-1.jpg' },
        { dest: 'Ciudad de México (MEX)', date: '28/06/2026', price: 82,  img: 'destination-2.jpg' },
        { dest: 'Puerto Vallarta (PVR)', date: '06/05/2026', price: 100, img: 'destination-3.jpg' },
        { dest: 'Roma (ITA)',            date: '14/07/2026', price: 250, img: 'destination-4.jpg' }
    ];
    return data.map(d => `
    <div class="card">
        <img src="assets/images/${d.img}" alt="${d.dest}">
        <div class="card-content">
            <h3>SAL → ${d.dest}</h3>
            <p>Salida ${d.date}</p>
            <span class="price">$${d.price} USD</span>
            <button onclick="goToReserve()">Reservar ahora</button>
        </div>
    </div>`).join('');
}

function setupSearch() {
    const btn = document.getElementById('search-btn');
    if (!btn) return;
    btn.addEventListener('click', () => {
        const origin = document.getElementById('search-origin')?.value || '';
        const dest   = document.getElementById('search-dest')?.value   || '';
        loadFlights(origin, dest);
    });
}

function goToReserve(flightId) {
    if (!isLoggedIn()) {
        window.location.href = 'login.html';
        return;
    }
    if (flightId) {
        window.location.href = `confirm.html?flightId=${flightId}`;
    } else {
        window.location.href = 'confirm.html';
    }
}

function setupLogout() {
    const btn = document.getElementById('btn-logout');
    if (btn) btn.addEventListener('click', logout);
}