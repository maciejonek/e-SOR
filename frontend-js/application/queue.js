document.addEventListener("DOMContentLoaded", function () {
    const colorElement = document.getElementById("color");
    const numberPeopleElement = document.getElementById("numberPeople");
    const placeElement = document.getElementById("place");
    const leaveQueueButton = document.getElementById("leave-queue");

    // Funkcja do pobierania danych kolejki z serwera
    function fetchQueueData() {
        fetch("http://localhost:8080/queue", {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${localStorage.getItem("token")}` // Token autoryzacyjny
            }
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Failed to fetch queue data. Status: " + response.status);
                }
                return response.json();
            })
            .then((data) => {
                updateQueueUI(data);
            })
            .catch((error) => {
                console.error("Error fetching queue data:", error);
                alert("An error occurred while fetching queue data.");
            });
    }

    // Funkcja do aktualizacji elementów na stronie
    function updateQueueUI(data) {
        colorElement.textContent = data.color || "N/A";
        numberPeopleElement.textContent = data.numberPeople || "N/A";
        placeElement.textContent = data.place || "N/A";
    }

    // Obsługa przycisku "leave queue"
    leaveQueueButton.addEventListener("click", function () {
        fetch("http://localhost:8080/queue/leave", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${localStorage.getItem("token")}` // Token autoryzacyjny
            }
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Failed to leave queue. Status: " + response.status);
                }
                return response.json();
            })
            .then(() => {
                alert("You have successfully left the queue.");
                // Po wyjściu z kolejki można odświeżyć stronę lub przekierować użytkownika
                fetchQueueData();
            })
            .catch((error) => {
                console.error("Error leaving queue:", error);
                alert("An error occurred while leaving the queue.");
            });
    });

    // Pobierz dane kolejki przy załadowaniu strony
    fetchQueueData();
});
