function handleFormSubmit(event) {
    event.preventDefault();

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    // Sprawdź, czy dane są poprawne
    if (email && password) {
        // Przesyłanie danych do backendu za pomocą fetch
        fetch('/api/login', { // Ustaw endpoint, który obsłuży login
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                email: email,
                password: password
            })
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    document.cookie = `user_email=${encodeURIComponent(email)}; path=/; samesite=Strict`;
                    window.location.href = 'dashboard.html'; // Przekierowanie na stronę główną
                } else {
                    alert('Invalid login or password');
                }
            })
            .catch(error => {
                console.error('Error:', error);
            });
    } else {
        alert('Please fill in all fields.');
    }
}

document.getElementById("loginForm").addEventListener("submit", handleFormSubmit);
