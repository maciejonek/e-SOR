import pandas as pd

# Wczytanie danych
file_path = "Triage.csv"
data = pd.read_csv(file_path, sep=';')

# Punktacja dla objawów
symptom_points = {
    'cc_abdominalpain': 1, 'cc_allergicreaction': 2, 'cc_animalbite': 2,
    'cc_ankleinjury': 1, 'cc_anklepain': 0, 'cc_arminjury': 1,
    'cc_armpain': 0, 'cc_armswelling': 1, 'cc_backpain': 0,
    'cc_bleeding/bruising': 3, 'cc_breastpain': 0, 'cc_breathingproblem': 2,
    'cc_burn': 2, 'cc_chestpain': 0, 'cc_coldlikesymptoms': 0,
    'cc_cough': 0, 'cc_dentalpain': 0, 'cc_dizziness': 1,
    'cc_earpain': 0, 'cc_elbowpain': 0, 'cc_emesis': 0,
    'cc_epistaxis': 0, 'cc_eyeinjury': 1, 'cc_eyepain': 0,
    'cc_eyeredness': 0, 'cc_facialinjury': 1, 'cc_facialpain': 0,
    'cc_facialswelling': 1, 'cc_fall': 2, 'cc_fall>65': 3,
    'cc_fatigue': 3, 'cc_fever': 2, 'cc_fever-75yearsorolder': 3,
    'cc_fingerinjury': 1, 'cc_fingerpain': 0, 'cc_fingerswelling': 0,
    'cc_flankpain': 0, 'cc_footinjury': 1, 'cc_footpain': 0,
    'cc_footswelling': 1, 'cc_generalizedbodyaches': 1, 'cc_groinpain': 0,
    'cc_hallucinations': 3, 'cc_handinjury': 1, 'cc_handpain': 0,
    'cc_headache': 0, 'cc_headinjury': 2, 'cc_hippain': 0,
    'cc_influenza': 1, 'cc_insectbite': 0, 'cc_irregularheartbeat': 2,
    'cc_jawpain': 0, 'cc_jointswelling': 1, 'cc_kneeinjury': 1,
    'cc_kneepain': 0, 'cc_leginjury': 1, 'cc_legpain': 0,
    'cc_legswelling': 1, 'cc_migraine': 0, 'cc_motorcyclecrash': 3,
    'cc_motorvehiclecrash': 3, 'cc_multiplefalls': 2, 'cc_nausea': 0,
    'cc_neckpain': 0, 'cc_oralswelling': 1, 'cc_palpitations': 3,
    'cc_pelvicpain': 1, 'cc_poisoning': 2, 'cc_rash': 0,
    'cc_rectalbleeding': 3, 'cc_rectalpain': 0, 'cc_ribinjury': 2,
    'cc_ribpain': 0, 'cc_seizures': 3, 'cc_shoulderinjury': 1,
    'cc_shoulderpain': 0, 'cc_skinirritation': 0, 'cc_sorethroat': 0,
    'cc_strokealert': 4, 'cc_swallowedforeignbody': 1, 'cc_syncope': 1,
    'cc_testiclepain': 0, 'cc_toeinjury': 0, 'cc_toepain': 0,
    'cc_vaginalbleeding': 2, 'cc_weakness': 0, 'cc_wheezing': 1,
    'cc_woundinfection': 2, 'cc_wristinjury': 1, 'cc_wristpain': 0
}

# Dodanie punktacji za objawy
def calculate_sum(row):
    total_points = 0
    for symptom, points in symptom_points.items():
        if symptom in row and row[symptom] > 0:
            total_points += points
    return total_points

data['sum'] = data.apply(calculate_sum, axis=1)

# Dodatkowe punkty za wiek >70
data['sum'] += data['age'].apply(lambda x: 2 if x > 70 else 0)

# Klasyfikacja na podstawie sumy punktów
def classify_row(row):
    if row['sum'] >= 6:
        return 'red'
    elif row['sum'] >= 4:
        return 'yellow'
    elif row['sum'] >= 2:
        return 'green'
    else:
        return 'blue'

data['classification'] = data.apply(classify_row, axis=1)

# Zapisanie wyników do pliku CSV
output_file = "Triage_classified.csv"
data.to_csv(output_file, index=False)

print(f"Dane zostały zaklasyfikowane i zapisane w pliku {output_file}.")
