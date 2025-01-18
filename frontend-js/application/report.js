document.getElementById("reportForm").addEventListener("submit", function(event) {
    event.preventDefault(); // Zapobiegamy domyślnej akcji formularza (wysłanie)

    // Zbieranie danych z formularza
    const firstName = document.getElementById("firstName").value;
    const lastName = document.getElementById("lastName").value;
    const pesel = document.getElementById("pesel").value;
    const age = document.getElementById("age").value;
    const numSurgeries = document.getElementById("numSurgeries").value;
    const symptoms = document.getElementById("symptoms").value;

    // if (!validatePesel(pesel)) {
    //     alert("Invalid PESEL number.");
    //     return;
    // }

    // Tworzenie obiektu danych do wysłania
    const reportData = {
        firstName: firstName,
        lastName: lastName,
        pesel: pesel,
        age: age,
        numSurgeries: numSurgeries,
        symptoms: symptoms
    };

    // Wysłanie danych do backendu
    fetch('https://localhost:8080/api/v1/report/', {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': 'https://localhost:3000/'
        },
        body: JSON.stringify(reportData)
    })
    .then(response => response.json())  // Odbieramy odpowiedź JSON
    .then(data => {
        if (data.success) {
            alert('Report submitted successfully!');
        } else {
            alert('Failed to submit report: ' + data.message);
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('An error occurred while submitting the report.');
    });
});

// Funkcja do walidacji PESEL
function validatePesel(pesel) {
    if (pesel.length !== 11 || isNaN(pesel)) {
        return false;
    }

    const weights = [1, 3, 7, 9, 1, 3, 7, 9, 1, 3];
    let sum = 0;

    for (let i = 0; i < 10; i++) {
        sum += parseInt(pesel.charAt(i)) * weights[i];
    }

    const controlNumber = 10 - (sum % 10);
    if (controlNumber === 10) controlNumber = 0;

    return controlNumber === parseInt(pesel.charAt(10));
}
