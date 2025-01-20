
// if (document.cookie.includes("JSESSIONID")) {
//     myProfile.style.display = "inline-block";
//     login.style.display = "none";
//     logOut.style.display = "inline-block"
// } else {
//     myProfile.style.display = "none";
//     login.style.display = "inline-block";
//     logOut.style.display = "none"
// }


function checkUserSession() {
    fetch('http://localhost:8080/myProfile', {
        method: 'GET',
        credentials: 'include',
        // headers: {
        //     'Content-Type': 'application/json'
        // }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('User not authenticated');
        }
        return response.json();
    })
    .then(userData => {
        console.log("Zalogowany użytkownik:", userData);
        document.getElementById("my-profile").style.display = "inline-block";
        document.getElementById("login").style.display = "none";
        document.getElementById("log-out").style.display = "inline-block";
    })
    .catch(error => {
        console.error("Błąd sesji:", error);
        document.getElementById("my-profile").style.display = "none";
        document.getElementById("login").style.display = "inline-block";
        document.getElementById("log-out").style.display = "none";
    });
}

// Wywołaj funkcję przy załadowaniu strony
window.onload = checkUserSession;
 