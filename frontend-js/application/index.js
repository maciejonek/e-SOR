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
       
        })
        .catch(error => {
            console.error("Błąd sesji:", error);
            document.getElementById("my-profile").style.display = "none";
            document.getElementById("report").style.display = "none";
            document.getElementById("log-out").style.display = "none";
            document.getElementById("login").style.display = "inline-block";
          
        });
}



document.getElementById('logoutForm')?.addEventListener('submit', (event) => {
    event.preventDefault();
    fetch('http://localhost:8080/logout', {
        method: 'POST',
        credentials: 'include',
    })
        .then(response => {
            if (response.ok) {
                console.log("Wylogowano poprawnie");
                window.location.href = 'login.html';
            } else {
                throw new Error('Wylogowanie nie powiodło się');
            }
        })
        .catch(error => console.error('Błąd podczas wylogowywania:', error));

});


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