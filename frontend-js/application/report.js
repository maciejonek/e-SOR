document.addEventListener("DOMContentLoaded", () => {
    const input = document.getElementById('myInput');
    if (input) {
      input.addEventListener('keyup', filterCheckboxes);
    }
  });

    console.log('Input value:', filter);
    console.log('Labels found:', labels);
function filterCheckboxes() {
    const input = document.getElementById('myInput'); // Pobranie pola wyszukiwania
    const filter = input.value.toUpperCase(); // Pobranie wartości wprowadzonej przez użytkownika
    const symptomsDiv = document.getElementById('symptoms'); // Pobranie kontenera z symptomami

    if (!symptomsDiv) {
        console.error("Element with ID 'symptoms' not found.");
        return;
    }

    const labels = symptomsDiv.getElementsByTagName('label'); // Pobranie wszystkich etykiet w kontenerze

for (let i = 0; i < labels.length; i++) {
        const label = labels[i];
        const txtValue = label.textContent || label.innerText;

        // Remove "cc_" prefix and compare
        const cleanText = txtValue.replace(/^cc_/, '').toUpperCase();
        if (cleanText.includes(filter)) {
            label.style.display = ""; // Show matching label
        } else {
            label.style.display = "none"; // Hide non-matching label
        }
    }
}

  
  
  // Obsługa formularza
  document.getElementById("reportForm").addEventListener("submit", function (event) {
    event.preventDefault(); // Zapobiegamy domyślnej akcji formularza
  
    // Pobieranie wartości z formularza
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
  
        if (data.status === "success" && data.prediction) {
          localStorage.setItem('prediction', data.prediction);
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
  