document.getElementById("reportForm").addEventListener("submit", function (event) {
    event.preventDefault(); // Zapobiegamy domyślnej akcji formularza

    // Zbieranie danych z formularza
    const firstName = document.getElementById("firstName").value;
    const lastName = document.getElementById("lastName").value;
    const pesel = document.getElementById("pesel").value;
    const age = document.getElementById("age").value;
    const numSurgeries = document.getElementById("numSurgeries").value;

    // Pobieranie zaznaczonych checkboxów
    const symptoms = Array.from(document.querySelectorAll('#symptoms input[type="checkbox"]:checked')).map(
        checkbox => checkbox.value
    );

    // Tworzenie obiektu danych do wysłania
    const formData = {
        first_name: firstName,
        last_name: lastName,
        pesel_number: pesel,
        age: parseInt(age, 10),
        num_surgeries: parseInt(numSurgeries, 10),
        symptoms: symptoms, // Tablica wybranych symptomów
    };

    // Wysłanie danych do backendu
    fetch('http://localhost:8080/api/v1/', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            if (data.success) {
                alert('Report submitted successfully!');
                window.location.href = "../templates/queue.html";
            } else {
                alert(`Failed to submit report: ${data.message}`);
            }
        })
        .catch(error => {
            console.error('Fetch error:', error);
            alert('An error occurred while submitting the report. Please check the server configuration.');
        });
});
