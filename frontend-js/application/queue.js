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

// Wyświetlanie danych w sekcji kolejki
function updateQueueInfo() {
    const prediction = getPrediction();

    if (prediction) {
        // Przypisz kolor na podstawie predykcji
        const color = mapPredictionToColor(prediction);

        // Pobierz dane kolejki
        const { numberPeople, place } = getQueueData(prediction);

        // Wyświetl dane na stronie
        document.getElementById('color').textContent = color;
        document.getElementById('numberPeople').textContent = numberPeople;
        document.getElementById('place').textContent = place;
    } else {
        // Jeśli brak danych, pokaż odpowiedni komunikat
        document.getElementById('queue-section').innerHTML = `
            <h2>Brak danych o kolejce</h2>
            <a href="../templates/index.html"><button id="main">Powrót</button></a>
        `;
    }
}

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
