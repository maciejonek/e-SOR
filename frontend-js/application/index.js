// // if (document.cookie.includes("JSESSIONID")) {
// //     myProfile.style.display = "inline-block";
// //     login.style.display = "none";
// //     logOut.style.display = "inline-block"
// // } else {
// //     myProfile.style.display = "none";
// //     login.style.display = "inline-block";
// //     logOut.style.display = "none"
// // }
//
//
// // function checkUserSession() {
// //     fetch('http://localhost:8080/myProfile', {
// //         method: 'GET',
// //         credentials: 'include',
// //         // headers: {
// //         //     'Content-Type': 'application/json'
// //         // }
// //     })
// //     .then(response => {
// //         if (!response.ok) {
// //             throw new Error('User not authenticated');
// //         }
// //         return response.json();
// //     })
// //     .then(userData => {
// //         console.log("Zalogowany użytkownik:", userData);
// //         document.getElementById("my-profile").style.display = "inline-block";
// //         document.getElementById("login").style.display = "none";
// //         document.getElementById("log-out").style.display = "inline-block";
// //     })
// //     .catch(error => {
// //         console.error("Błąd sesji:", error);
// //         document.getElementById("my-profile").style.display = "none";
// //         document.getElementById("login").style.display = "inline-block";
// //         document.getElementById("log-out").style.display = "none";
// //     });
// // }
// //
// // // Wywołaj funkcję przy załadowaniu strony
// // window.onload = checkUserSession;
// //
//
// function checkUserSession() {
//     fetch('http://localhost:8080/api/v1/auth/check', {  // Use your /check endpoint for a simpler authentication check
//         method: 'GET',
//         credentials: 'include', // This is crucial to send cookies with the request
//     })
//         .then(response => {
//             if (!response.ok) {
//                 throw new Error('User not authenticated');
//             }
//             return response.text(); // We only check for the status, no need to parse JSON
//         })
//         .then(() => { // No need for userData in this case, just check if the request was successful
//             console.log("Zalogowany użytkownik");
//             // If user is logged in
//             document.getElementById("my-profile").style.display = "inline-block";
//             document.getElementById("report").style.display = "inline-block";
//             document.getElementById("log-out").style.display = "inline-block";
//
//             // Hide "Log me" and "Register" buttons
//             document.getElementById("login").style.display = "none";
//             document.getElementById("register").style.display = "none";
//         })
//         .catch(error => {
//             console.error("Błąd sesji:", error);
//             // If user is not logged in
//             document.getElementById("my-profile").style.display = "none";
//             document.getElementById("report").style.display = "none";
//             document.getElementById("log-out").style.display = "none";
//
//             // Show "Log me" and "Register" buttons
//             document.getElementById("login").style.display = "inline-block";
//             document.getElementById("register").style.display = "inline-block";
//         });
// }
//
//
// function sayHello() {
//     fetch('http://localhost:8080/api/v1/auth/check', {  // Use your /check endpoint for a simpler authentication check
//         method: 'GET',
//         credentials: 'include', // This is crucial to send cookies with the request
//     }).then(response => {
//         if (!response.ok) {
//             throw new Error('User not authenticated');
//         }
//         return response.text();
//     }).then(() => {
//         fetch('http://localhost:8080/getUserName', {
//             method: 'GET',
//             credentials: 'include',
//         })
//             .then(response => {
//                 if (!response.ok) {
//                     throw new Error('User not authenticated');
//                 }
//                 return response.text();
//             })
//             .then(userName => {
//                 console.log("User name:", userName);
//             })
//             .catch(error => {
//                 console.error("Error:", error);
//             });
//     }).catch(error => {
//         console.error("Error:", error);
//     });
// }
//
// window.onload = function() {
//     checkUserSession();
//     sayHello();
// };

function checkUserSession() {
    fetch('http://localhost:8080/api/v1/auth/check', {
        method: 'GET',
        credentials: 'include',
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('User not authenticated');
            }
        })
        .then(userName => {
            console.log("Zalogowany użytkownik:", userName);
            document.getElementById("my-profile").style.display = "inline-block";
            document.getElementById("report").style.display = "inline-block";
            document.getElementById("log-out").style.display = "inline-block";
            document.getElementById("login").style.display = "none";
            document.getElementById("register").style.display = "none";
        })
        .catch(error => {
            console.error("Błąd sesji:", error);
            document.getElementById("my-profile").style.display = "none";
            document.getElementById("report").style.display = "none";
            document.getElementById("log-out").style.display = "none";
            document.getElementById("login").style.display = "inline-block";
            document.getElementById("register").style.display = "inline-block";
        });
}

function handleFormSubmit(event) {
    event.preventDefault();
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const formData = {
        username: email,
        password: password
    };

    fetch('http://localhost:8080/login', {
        method: 'POST',
        credentials: 'include',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(formData)
    })
        .then(response => {
            if (response.ok) {
                console.log("Poprawnie zalogowano");
                window.location.href = 'index.html';
            } else {
                return response.text().then(text => { throw new Error(text); });
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Error occurred while logging in: ' + error.message);
        });
}

window.onload = function() {
    checkUserSession();
};