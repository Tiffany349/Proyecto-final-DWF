document.addEventListener("DOMContentLoaded", loadProfile);

async function loadProfile() {
    const user = getCurrentUser();

    document.getElementById("fullname").value = user.fullname;
    document.getElementById("email").value = user.email;
    document.getElementById("role").value = user.role;

    document.getElementById("profile-form")
        .addEventListener("submit", updateProfile);
}

async function updateProfile(e) {
    e.preventDefault();

    const msg = document.getElementById("profile-msg");

    try {
        const users = await Users.getAll();

        const current = users.find(u => u.email === getCurrentUser().email);

        if (!current) throw new Error("Usuario no encontrado");

        const updated = await request("PUT", `/users/${current.id}`, {
            fullname: document.getElementById("fullname").value,
            email: document.getElementById("email").value
        });

        localStorage.setItem("fullname", updated.fullname);
        localStorage.setItem("email", updated.email);

        msg.innerHTML = "<p style='color:green;'>Perfil actualizado correctamente</p>";

    } catch (err) {
        msg.innerHTML = `<p style='color:red;'>${err.message}</p>`;
    }
}