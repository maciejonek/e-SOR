document.getElementById("reportForm").addEventListener("submit", function (event) {
    event.preventDefault(); // Zapobiegamy domyślnej akcji formularza

    // Zbieranie danych z formularza
    const age = document.getElementById("age").value;
    const numSurgeries = document.getElementById("numSurgeries").value;

    // Pobieranie zaznaczonych checkboxów
    const symptoms = Array.from(document.querySelectorAll('#symptoms input[type="checkbox"]:checked')).map(
        checkbox => checkbox.value
    );

    // Tworzenie dynamicznego obiektu z symptomami
    const symptomsObject = symptoms.reduce((acc, symptom) => {
        acc[symptom] = 1; // Każdy zaznaczony symptom ma wartość 1
        return acc;
    }, {});

    // Tworzenie obiektu danych do wysłania
    const formData = {
        facility_id: 1, // ID placówki
        age: parseInt(age, 10),
        n_surgeries: parseInt(numSurgeries, 10),
        ...symptomsObject // Dodanie dynamicznie utworzonych symptomów
    };

    // Logowanie JSON-a w konsoli
    console.log("Wysyłane dane JSON:", JSON.stringify(formData, null, 2));

    // Wysłanie danych do backendu
    fetch('http://localhost:8080/predictAndSave', {
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
            console.log("Odpowiedź serwera:", data);

            // Użyj właściwego warunku na podstawie `status`
            if (data.status === "success" && data.prediction) {
                // Zapisz predykcję w Local Storage
                localStorage.setItem('prediction', data.prediction);

                // Przekierowanie na queue.html
                window.location.href = `queue.html`;
            } else {
                alert(`Failed to submit report: ${data.message || "Unknown error"}`);
            }
        })
        .catch(error => {
            console.error('Fetch error:', error);
            alert('An error occurred while submitting the report. Please check the server configuration.');
        });
});
