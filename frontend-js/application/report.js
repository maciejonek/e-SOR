<<<<<<< HEAD
document.addEventListener("DOMContentLoaded", () => {
    const input = document.getElementById('myInput');
    if (input) {
      input.addEventListener('keyup', filterCheckboxes);
    }
  });
function filterCheckboxes() {
    const input = document.getElementById('myInput'); 
    const filter = input.value.toUpperCase();
    const symptomsDiv = document.getElementById('symptoms'); 

    if (!symptomsDiv) {
        console.error("Element with ID 'symptoms' not found.");
        return;
    }

    const labels = symptomsDiv.getElementsByTagName('label'); 

for (let i = 0; i < labels.length; i++) {
        const label = labels[i];
        const txtValue = label.textContent || label.innerText;

        const cleanText = txtValue.replace(/^cc_/, '').toUpperCase();
        if (cleanText.includes(filter)) {
            label.style.display = ""; 
        } else {
            label.style.display = "none"; 
        }
    }
}
=======
function reportForm(data) {
    document.getElementById("firstName").value = data.name || '';
    document.getElementById("lastName").value = data.surname || '';
    document.getElementById("pesel").value = data.peselNumber || '';
    document.getElementById("age").value = data.age || '';
}

fetch("http://localhost:8080/myProfile", {
    method: "GET",
    credentials: "include"
})
    .then(response => response.json())
    .then(data => {
        reportForm(data);
    })
    .catch(error => {
        console.error("Error:", error);
    });


>>>>>>> f85efb20d9fff180c598f5cb1191f42e0c84b950
document.getElementById("reportForm").addEventListener("submit", function (event) {
    event.preventDefault();

    const age = document.getElementById("age").value;
    const numSurgeries = document.getElementById("numSurgeries").value;

    const symptoms = Array.from(document.querySelectorAll('#symptoms input[type="checkbox"]:checked')).map(
        checkbox => checkbox.value
    );

    const symptomsObject = symptoms.reduce((acc, symptom) => {
        acc[symptom] = 1; 
        return acc;
    }, {});

    const formData = {
        facility_id: 1, 
        age: parseInt(age, 10),
        n_surgeries: parseInt(numSurgeries, 10),
        ...symptomsObject 
    };

    console.log("Wysyłane dane JSON:", JSON.stringify(formData, null, 2));

    fetch('http://localhost:8080/predictAndSave', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        credentials: "include",
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
