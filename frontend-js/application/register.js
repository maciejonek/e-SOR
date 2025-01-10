async function handleFormSubmit(){
    event.preventDefault(); // Zapobiega domyślnemu przesłaniu formularza

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirm_password").value;
    const pesel = document.getElementById("pesel").value;
    const phoneNumber = document.getElementById("phone_number").value;

    if (!validatePassword() || !validatePesel()) {
        return;
    }

    const data = {
        firstName: "John",
        lastName: "Doe",
        email: email,
        password: password,
    };

    try {
        // Wysłanie żądania POST do backendu
        const response = await fetch("http://localhost:8080/api/v1/registration", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(data),
        });

    // Obsługa odpowiedzi z backendu
    if (response.ok) {
        const result = await response.text(); // Odczytaj token lub wiadomość
        alert(`Registration successful! Token: ${result}`);
    } else {
        const errorMessage = await response.text();
        alert(`Error: ${errorMessage}`);
    }
    } catch (error) {
        alert("An error occurred while processing the request.");
        console.error(error);
    }
}

document.querySelector("form").addEventListener("submit", handleFormSubmit);

    
function validatePassword(){
    var password =  document.getElementById("password").value;
    var confirm_password =  document.getElementById("confirm_password").value;

    if(password.length < 8 || password.search(/[a-z]/) < 0 || password.search(/[A-Z]/) < 0 || password.search(/[0-9]/) < 0) { 
        alert("Password requirements:\n- At least 8 characters long.\n- At least one uppercase letter, one lowercase letter, and one number.");
        document.getElementById("password").value="";
        document.getElementById("confirm_password").value="";
        return false;
      
    } else if(password !== confirm_password){
        alert("Error: Passwords do not match, please try again");
        document.getElementById("confirm_password").value="";
        return false;
    }
    return true;
}

function validatePesel(){
    var pesel = document.getElementById("pesel").value;

    if(pesel.length!==11 || isNaN(pesel)){
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

    if(controlNumber === parseInt(pesel.charAt(10))){
        return true;
    } else {
        alert("Error: Pesel number is invalid");
        return false;
    }
}