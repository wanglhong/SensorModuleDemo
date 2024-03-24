import Http from "@/api/http.js"

export const login = function(loginForm) {
    return Http.post("/api/upms/sys/login", loginForm)
}

export const menu = function() {
    return Http.get("/api/upms/sys/menu")
}

export const permission = function() {
    return Http.get("/api/upms/sys/permissions")
}
