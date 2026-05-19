const API_BASE_URL = "http://localhost:8080/api";

function getToken() { return localStorage.getItem("token"); }
function isLoggedIn() { return !!getToken(); }
function isAdmin() { return localStorage.getItem("role") === "ADMIN"; }

function saveSession(data) {
    localStorage.setItem("token", data.token);
    if (data.role)     localStorage.setItem("role", data.role);
    if (data.fullname) localStorage.setItem("fullname", data.fullname);
    if (data.email)    localStorage.setItem("email", data.email);
}

function logout() {
    localStorage.removeItem("token");
    localStorage.removeItem("role");
    localStorage.removeItem("fullname");
    localStorage.removeItem("email");
    window.location.href = "index.html";
}

function getCurrentUser() {
    return {
        email:    localStorage.getItem("email")    || "",
        fullname: localStorage.getItem("fullname") || "",
        role:     localStorage.getItem("role")     || "USER"
    };
}

function requireAuth() {
    if (!isLoggedIn()) window.location.href = "login.html";
}

function authHeaders() {
    const h = { "Content-Type": "application/json" };
    const t = getToken();
    if (t) h["Authorization"] = `Bearer ${t}`;
    return h;
}

async function request(method, path, body = null) {
    const opts = { method, headers: authHeaders() };
    if (body) opts.body = JSON.stringify(body);

    const res = await fetch(`${API_BASE_URL}${path}`, opts);

    if (res.status === 401) {
        localStorage.removeItem("token");
        window.location.href = "login.html";
        return;
    }

    if (res.status === 204) return null;

    const data = await res.json().catch(() => null);

    if (!res.ok) throw new Error(data?.message || data?.error || `Error ${res.status}`);
    return data;
}

const Auth = {
    login(email, password)              { return request("POST", "/auth/login",    { email, password }); },
    register(fullname, email, password) { return request("POST", "/auth/register", { fullname, email, password }); }
};

const Flights = {
    getAll()         { return request("GET",    "/flights"); },
    getById(id)      { return request("GET",    `/flights/${id}`); },
    create(body)     { return request("POST",   "/flights", body); },
    update(id, body) { return request("PUT",    `/flights/${id}`, body); },
    delete(id)       { return request("DELETE", `/flights/${id}`); }
};

const Reservations = {
    getAll()     { return request("GET",  "/reservations"); },
    create(body) { return request("POST", "/reservations", body); },
    cancel(id)   { return request("PUT",  `/reservations/cancel/${id}`); }
};

const Passengers = {
    getAll()      { return request("GET",    "/passengers"); },
    getById(id)   { return request("GET",    `/passengers/${id}`); },
    create(body)  { return request("POST",   "/passengers", body); },
    update(id, b) { return request("PUT",    `/passengers/${id}`, b); },
    delete(id)    { return request("DELETE", `/passengers/${id}`); }
};

const Airlines = {
    getAll()      { return request("GET",    "/airlines"); },
    create(body)  { return request("POST",   "/airlines", body); },
    update(id, b) { return request("PUT",    `/airlines/${id}`, b); },
    delete(id)    { return request("DELETE", `/airlines/${id}`); }
};

const Payments = {
    getAll()     { return request("GET",  "/payments"); },
    create(body) { return request("POST", "/payments", body); }
};

const Claims = {
    getAll()                    { return request("GET",  "/claims"); },
    create(body)                { return request("POST", "/claims", body); },
    updateStatus(id, status)    { return request("PUT",  `/claims/${id}/status?status=${status}`); }
};

const Users = {
    getAll()       { return request("GET",    "/users"); },
    activate(id)   { return request("PUT",    `/users/${id}/activate`); },
    deactivate(id) { return request("PUT",    `/users/${id}/deactivate`); },
    delete(id)     { return request("DELETE", `/users/${id}`); }
};

function updateNavbar() {
    const navLogin  = document.getElementById("nav-login");
    const navTrips  = document.getElementById("nav-trips");
    const navLogout = document.getElementById("nav-logout");
    const navAdmin  = document.getElementById("nav-admin");

    if (isLoggedIn()) {
        if (navLogin)  navLogin.style.display  = "none";
        if (navTrips)  navTrips.style.display  = "list-item";
        if (navLogout) navLogout.style.display = "list-item";
        if (navAdmin)  navAdmin.style.display  = isAdmin() ? "list-item" : "none";
    } else {
        if (navLogin)  navLogin.style.display  = "list-item";
        if (navTrips)  navTrips.style.display  = "none";
        if (navLogout) navLogout.style.display = "none";
        if (navAdmin)  navAdmin.style.display  = "none";
    }
}