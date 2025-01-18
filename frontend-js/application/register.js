function handleFormSubmit(event) {
    event.preventDefault();

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const confirmedPassword = document.getElementById("confirmedPassword").value;
    const firstName = document.getElementById("firstName").value;
    const lastName = document.getElementById("lastName").value;
    const pesel = document.getElementById("pesel").value;
    const phoneNumber = document.getElementById("phone_number").value;

    const formData = {
        firstName: firstName,
        lastName: lastName,
        email: email,
        password: password,
        confirmedPassword: confirmedPassword,
        pesel: pesel,
        phoneNumber: phoneNumber // Uncomment this line if you want to include phone number
    };

    fetch('http://localhost:8080/api/v1/registration', {
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
        });
}

// Funkcja walidacji hasła
function validatePassword(password, confirmPassword) {
    console.log('Validating password:', password);
    if (password.length < 8 || password.search(/[a-z]/) < 0 || password.search(/[A-Z]/) < 0 || password.search(/[0-9]/) < 0) {
        alert("Password requirements:\n- At least 8 characters long.\n- At least one uppercase letter, one lowercase letter, and one number.");
        document.getElementById("password").value = "";
        document.getElementById("confirm_password").value = "";
        return false;
    } else if (password !== confirmPassword) {
        alert("Error: Passwords do not match, please try again");
        document.getElementById("confirm_password").value = "";
        return false;
    }
    return true;
}

// Funkcja walidacji PESEL
function validatePesel(pesel) {
    console.log('Validating PESEL:', pesel);
    if (pesel.length !== 11 || isNaN(pesel)) {
        alert("Error: Pesel must be 11 digits");
        return false;
    }

    var weights = [1, 3, 7, 9, 1, 3, 7, 9, 1, 3];
    var sum = 0;
    for (var i = 0; i < 10; i++) {
        sum += parseInt(pesel.charAt(i)) * weights[i];
    }

    var controlNumber = 10 - (sum % 10);
    if (controlNumber === 10) controlNumber = 0;

    if (controlNumber === parseInt(pesel.charAt(10))) {
        return true;
    } else {
        alert("Error: Pesel number is invalid");
        return false;
    }
}
