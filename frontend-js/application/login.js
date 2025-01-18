function handleFormSubmit(event) {
    event.preventDefault();

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    const formData = {
        email: email,
        password: password
    };

    // Sprawdzamy, czy wypełniono wszystkie pola
    if (email && password) {
        fetch('http://localhost:8080/login', { 
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(formData)
        })
        .then(response => response.json())
        .then(data => {
            if (data.message) {
                alert(data.message);
                throw new Error(data.message);
            } else if (data.token) {
                // Zmiana przekierowania na myprofile.html po rejestracji
                window.location.href = 'myprofile.html'; // Zmieniono na myprofile.html
            } else {
                alert('Unexpected response from the server.');
                throw new Error('Unexpected response format.');
            }
        })
        .catch(error => {
                console.error('Error:', error);
                alert('Error occurred while logging in.');
        });
    } else {
        alert('Please fill in all fields.');
    }
}


