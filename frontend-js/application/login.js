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
    .then(response => response.json())
    .then(data => {
        if (data.message) {
            alert(data.message);
            throw new Error(data.message);
        } else if (data.token) {
            document.cookie = `user_email=${encodeURIComponent(email)}; path=/; samesite=Strict`;
            console.log("Poprawnie zalogowano");
            window.location.href = 'index.html';
        } else {
            alert('Unexpected response from the server.');
            throw new Error('Unexpected response format.');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Error occurred while logging in.');
    })
}

