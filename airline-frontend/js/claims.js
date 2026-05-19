document.addEventListener("DOMContentLoaded", () => {
    updateNavbar();

    const user = getCurrentUser();

    document.getElementById("email").value = user.email || "";
    document.getElementById("passengerName").value = user.fullname || "";

    document.getElementById("claim-form")
        .addEventListener("submit", submitClaim);

    document.getElementById("load-claims")
        .addEventListener("click", loadClaims);
});

async function submitClaim(e) {
    e.preventDefault();

    const msg = document.getElementById("claim-msg");

    try {
        await Claims.create({
            passengerName: document.getElementById("passengerName").value,
            email: document.getElementById("email").value,
            claimType: document.getElementById("claimType").value,
            flightNumber: document.getElementById("flightNumber").value,
            origin: document.getElementById("origin").value,
            destination: document.getElementById("destination").value,
            description: document.getElementById("description").value
        });

        msg.innerHTML = "<p style='color:green;'>Reclamo enviado correctamente</p>";

        document.getElementById("description").value = "";

    } catch (err) {
        msg.innerHTML = `<p style='color:red;'>${err.message}</p>`;
    }
}

async function loadClaims() {
    const email = document.getElementById("email").value;
    const list = document.getElementById("claims-list");

    try {
        const claims = await Claims.getByEmail(email);

        if (!claims.length) {
            list.innerHTML = "<p>No tienes reclamos registrados.</p>";
            return;
        }

        list.innerHTML = claims.map(c => `
            <div class="trip-card-custom">
                <h4>${c.claimType}</h4>
                <p>${c.description}</p>
                <p><strong>Vuelo:</strong> ${c.flightNumber || "N/A"}</p>
                <p><strong>Estado:</strong> ${c.status}</p>
            </div>
        `).join("");

    } catch (err) {
        list.innerHTML = `<p style="color:red;">${err.message}</p>`;
    }
}