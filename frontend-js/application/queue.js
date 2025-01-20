// Funkcja do odczytu predykcji z Local Storage
function getPrediction() {
    return localStorage.getItem('prediction');
}

// Funkcja do pobierania danych z endpointu /queue
async function fetchQueueData() {
    try {
        const response = await fetch("http://localhost:8080/queue", {
            method: "GET",
            credentials: "include"
        });

        if (!response.ok) {
            throw new Error("Failed to fetch queue data");
        }

        const data = await response.json();
        console.log("Data fetched:", data);

        // Jeśli nie było jeszcze zapisanego szacowanego czasu, zapisujemy go do localStorage
        if (!localStorage.getItem('estimatedTime')) {
            localStorage.setItem('estimatedTime', data.estimatedTime);
        }

        return data;
    } catch (error) {
        console.error("Error fetching queue data:", error);
        return null;
    }
}

// Funkcja do aktualizacji informacji o kolejce
async function updateQueueInfo() {
    const queueData = await fetchQueueData();

    if (queueData) {
        const {totalQueueSize, userPosition} = queueData;
        const estimatedTime = localStorage.getItem('estimatedTime');  // Odczytujemy zapisany czas

        const color = getPrediction();

        const colorElement = document.getElementById('color');
        if (color) {
            let colorName = ""; // Variable to store the color name
            switch (parseInt(color)) {
                case 1:
                    colorName = "Red";
                    colorElement.style.color = 'red';
                    break;
                case 2:
                    colorName = "Orange";
                    colorElement.style.color = 'orange';
                    break;
                case 3:
                    colorName = "Yellow";
                    colorElement.style.color = 'yellow';
                    break;
                case 4:
                    colorName = "Green";
                    colorElement.style.color = 'green';
                    break;
                case 5:
                    colorName = "Blue";
                    colorElement.style.color = 'blue';
                    break;
                default:
                    colorName = "Unknown";
                    colorElement.style.color = 'black';
            }
            colorElement.textContent = colorName;
        }

        // Ustawianie pozostałych wartości
        document.getElementById('totalQueueSize').textContent = totalQueueSize || "N/A";
        document.getElementById('userPosition').textContent = userPosition || "N/A";
        document.getElementById('estimatedTime').textContent = estimatedTime || "N/A";
    } else {
        document.getElementById('queue-section').innerHTML = `
            <h2>Nie udało się załadować danych kolejki</h2>
            <a href="../templates/index.html"><button id="main">Powrót</button></a>
        `;
    }
}


async function leaveQueue() {
    try {
        // Wysyłamy zapytanie do backendu, aby usunąć użytkownika z bazy danych
        const response = await fetch(`http://localhost:8080/queue/leave`, {
            method: 'DELETE',
            credentials: 'include'
        });

        if (response.ok) {
            console.log("User removed from queue successfully");
            // Usuwanie danych z localStorage
            localStorage.removeItem('prediction');
            window.location.href = "../templates/index.html"; // Przekierowanie do głównej strony
        } else {
            console.error("Failed to remove user from queue");
        }
    } catch (error) {
        console.error("Error leaving queue:", error);
    }
}


// Nasłuchiwanie kliknięcia przycisku "leave queue"
document.getElementById('leave-queue').addEventListener('click', leaveQueue);

// Inicjalizacja danych kolejki po załadowaniu strony
updateQueueInfo();
setInterval(updateQueueInfo, 10000); // Odświeżanie danych co 10 sekund
