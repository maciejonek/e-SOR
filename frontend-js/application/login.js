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
            console.log("Poprawnie zalogowano");
            window.location.href = '/templates/myprofile.html';
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

// const loginForm = document.getElementById('loginForm');
//     loginForm.addEventListener('submit', async function(event) {
//         event.preventDefault();

//         const formData = new FormData(loginForm);
//         const loginData = Object.fromEntries(formData);

//         try {
//             const response = await fetch('http://localhost:8080/api/v1/login', {
//                 method: 'POST',
//                 headers: { 'Content-Type': 'application/json' },
//                 body: JSON.stringify(loginData),
//                 credentials: 'include' // Important for session-based auth
//             });

//             if (response.ok) {
//                 alert('Login successful!');
//                 window.location.href = "http://localhost:3000/templates/index.html";
//             } else {
//                 const error = await response.text();
//                 alert('Login failed: ' + error);
//             }
//         } catch (err) {
//             console.error('Error:', err);
//             alert('An error occurred during login.');
//         }
//     });

