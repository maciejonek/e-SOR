// Funkcja do odczytu predykcji z Local Storage
function getPrediction() {
    return localStorage.getItem('prediction');
}

// Funkcja do mapowania predykcji na kolor
function mapPredictionToColor(prediction) {
    const colors = {
        1: 'Red',
        2: 'Orange',
        3: 'Yellow',
        4: 'Green',
        5: 'Blue'
    };
    return colors[prediction] || 'Unknown';
}
async function updateQueueInfoFromBackend() {
    try {
        // Wysyłanie zapytania do backendu
        const response = await fetch('/queue');
        const data = await response.json();

        if (data.status === "success") {
            // Wyciąganie danych z odpowiedzi
            const { position, queueSize } = data;

            // Mapowanie pozycji na kolor
            const color = mapPredictionToColor(position);

            // Aktualizacja sekcji kolejki
            document.getElementById('color').textContent = color;
            document.getElementById('numberPeople').textContent = queueSize;
            document.getElementById('place').textContent = position;

            // Zapis w Local Storage (opcjonalnie, jeśli chcesz przechować te dane lokalnie)
            localStorage.setItem('prediction', position);
        } else {
            // Obsługa błędów po stronie serwera
            document.getElementById('queue-section').innerHTML = `
                <h2>Błąd pobierania danych o kolejce</h2>
                <p>${data.message}</p>
                <a href="../templates/index.html"><button id="main">Powrót</button></a>
            `;
        }
    } catch (error) {
        // Obsługa błędów sieciowych
        document.getElementById('queue-section').innerHTML = `
            <h2>Błąd połączenia z serwerem</h2>
            <p>${error.message}</p>
            <a href="../templates/index.html"><button id="main">Powrót</button></a>
        `;
    }
}
document.addEventListener('DOMContentLoaded', () => {
    updateQueueInfoFromBackend();

    // Obsługa przycisku "leave queue"
    const leaveButton = document.getElementById('leave-queue');
    if (leaveButton) {
        leaveButton.addEventListener('click', leaveQueue);
    }
});

// Funkcja obsługująca wyjście z kolejki
function leaveQueue() {
    // Czyścimy dane o predykcji z Local Storage
    localStorage.removeItem('prediction');

    // Przekierowanie na stronę główną
    window.location.href = "../templates/index.html";
}

// Nasłuchiwanie kliknięcia przycisku "leave queue"
document.getElementById('leave-queue').addEventListener('click', leaveQueue);

// Inicjalizacja danych kolejki po załadowaniu strony
updateQueueInfo();
