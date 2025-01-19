const myProfile = document.getElementById("my-profile")
const login = document.getElementById("login")
const logOut = document.getElementById("logOut")

if (document.cookie.includes("user_email=")) {
    myProfile.style.display = "inline-block";
    login.style.display = "none";
    logOut.style.display = "inline-block"
} else {
    myProfile.style.display = "none";
    login.style.display = "inline-block";
    logOut.style.display = "none"
}

myButton.addEventListener("click", () => {
    const cookies = document.cookie.split('; ');

    for (let cookie of cookies) {
        // Podziel nazwę i wartość ciasteczka
        const [name, value] = cookie.split('=');

        // Sprawdź, czy wartość ciasteczka zawiera poszukiwany ciąg
        if (value && value.includes("user_email=")) {
            // Usuń ciasteczko ustawiając datę wygaśnięcia w przeszłości
            document.cookie = `${name}=; path=/; expires=Thu, 01 Jan 1970 00:00:00 UTC;`
        }
    }
})