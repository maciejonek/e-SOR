import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.ensemble import RandomForestClassifier
from sklearn.metrics import classification_report, accuracy_score
from sklearn.preprocessing import LabelEncoder

# Wczytanie danych
file_path = "Triage_classified.csv"
data = pd.read_csv(file_path, sep=',')
#kolumny do usuniecia
columns_to_drop = [
    'gender',
    'arrivalmonth',
    'arrivalday',
    'arrivalhour_bin',
    'sum',
    'classification'
]
# Wybór zmiennych wejściowych
X = data.drop(columns=columns_to_drop)

# Kodowanie etykiet (czerwony, żółty, zielony, niebieski) na liczby
label_encoder = LabelEncoder()
y = label_encoder.fit_transform(data['classification'])  # Klasyfikacja jako zmienna wyjściowa

# Podział danych na zbiór treningowy i testowy
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

# Trenowanie modelu - Random Forest
model = RandomForestClassifier(n_estimators=100, random_state=42)
model.fit(X_train, y_train)

# Predykcje na zbiorze testowym
y_pred = model.predict(X_test)

# Ocena modelu
print("Ocena modelu:")
print("Dokładność:", accuracy_score(y_test, y_pred))



