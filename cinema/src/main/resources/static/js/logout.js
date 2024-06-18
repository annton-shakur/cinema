function logout() {
    console.log("Logout function called");
    fetch("/logout", {
        method: "POST",
        credentials: "include"
    })
    .then(response => {
        console.log("Response received:", response);
        if (response.ok) {
            document.cookie = "jwtToken=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;";

            localStorage.removeItem("jwtToken");

            window.location.href = "/login";
        } else {
            console.error("Logout failed! Response status:", response.status);
        }
    })
    .catch(error => {
        console.error("Error during logout:", error);
    });
}
