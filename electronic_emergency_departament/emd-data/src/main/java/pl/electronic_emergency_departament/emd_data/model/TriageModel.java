package pl.electronic_emergency_departament.emd_data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;


@Data
@Entity
public class TriageModel {

    @Id
    @GeneratedValue
    private int id;
    private int age = 0;
    private int n_surgeries = 0;
    private int cc_abdominalcramping = 0;
    private int cc_abdominaldistention = 0;
    private int cc_abdominalpain = 0;
    private int cc_abdominalpainpregnant = 0;
    private int cc_abnormallab = 0;
    private int cc_abscess = 0;
    private int cc_addictionproblem = 0;
    private int cc_agitation = 0;
    private int cc_alcoholintoxication = 0;
    private int cc_alcoholproblem = 0;
    private int cc_allergicreaction = 0;
    private int cc_alteredmentalstatus = 0;
    private int cc_animalbite = 0;
    private int cc_ankleinjury = 0;
    private int cc_anklepain = 0;
    private int cc_anxiety = 0;
    private int cc_arminjury = 0;
    private int cc_armpain = 0;
    private int cc_armswelling = 0;
    private int cc_assaultvictim = 0;
    private int cc_asthma = 0;
    private int cc_backpain = 0;
    private int cc_bleeding_bruising = 0;
    private int cc_blurredvision = 0;
    private int cc_bodyfluidexposure = 0;
    private int cc_breastpain = 0;
    private int cc_breathingdifficulty = 0;
    private int cc_breathingproblem = 0;
    private int cc_burn = 0;
    private int cc_cardiacarrest = 0;
    private int cc_cellulitis = 0;
    private int cc_chestpain = 0;
    private int cc_chesttightness = 0;
    private int cc_chills = 0;
    private int cc_coldlikesymptoms = 0;
    private int cc_confusion = 0;
    private int cc_conjunctivitis = 0;
    private int cc_constipation = 0;
    private int cc_cough = 0;
    private int cc_cyst = 0;
    private int cc_decreasedbloodsugar_symptomatic = 0;
    private int cc_dehydration = 0;
    private int cc_dentalpain = 0;
    private int cc_depression = 0;
    private int cc_detoxevaluation = 0;
    private int cc_diarrhea = 0;
    private int cc_dizziness = 0;
    private int cc_drug_alcoholassessment = 0;
    private int cc_drugproblem = 0;
    private int cc_dyspnea = 0;
    private int cc_dysuria = 0;
    private int cc_earpain = 0;
    private int cc_earproblem = 0;
    private int cc_edema = 0;
    private int cc_elbowpain = 0;
    private int cc_elevatedbloodsugar_nosymptoms = 0;
    private int cc_elevatedbloodsugar_symptomatic = 0;
    private int cc_emesis = 0;
    private int cc_epigastricpain = 0;
    private int cc_epistaxis = 0;
    private int cc_exposuretostd = 0;
    private int cc_extremitylaceration = 0;
    private int cc_extremityweakness = 0;
    private int cc_eyeinjury = 0;
    private int cc_eyepain = 0;
    private int cc_eyeproblem = 0;
    private int cc_eyeredness = 0;
    private int cc_facialinjury = 0;
    private int cc_faciallaceration = 0;
    private int cc_facialpain = 0;
    private int cc_facialswelling = 0;
    private int cc_fall = 0;
    private int cc_fall_65 = 0;
    private int cc_fatigue = 0;
    private int cc_femaleguproblem = 0;
    private int cc_fever = 0;
    private int cc_fever_75yearsorolder = 0;
    private int cc_fever_9weeksto74years = 0;
    private int cc_feverimmunocompromised = 0;
    private int cc_fingerinjury = 0;
    private int cc_fingerpain = 0;
    private int cc_fingerswelling = 0;
    private int cc_flankpain = 0;
    private int cc_follow_upcellulitis = 0;
    private int cc_footinjury = 0;
    private int cc_footpain = 0;
    private int cc_footswelling = 0;
    private int cc_foreignbodyineye = 0;
    private int cc_fulltrauma = 0;
    private int cc_generalizedbodyaches = 0;
    private int cc_gibleeding = 0;
    private int cc_giproblem = 0;
    private int cc_groinpain = 0;
    private int cc_hallucinations = 0;
    private int cc_handinjury = 0;
    private int cc_handpain = 0;
    private int cc_headache = 0;
    private int cc_headache_newonsetornewsymptoms = 0;
    private int cc_headache_recurrentorknowndxmigraines = 0;
    private int cc_headachere_evaluation = 0;
    private int cc_headinjury = 0;
    private int cc_headlaceration = 0;
    private int cc_hematuria = 0;
    private int cc_hemoptysis = 0;
    private int cc_hippain = 0;
    private int cc_homicidal = 0;
    private int cc_hyperglycemia = 0;
    private int cc_hypertension = 0;
    private int cc_hypotension = 0;
    private int cc_influenza = 0;
    private int cc_ingestion = 0;
    private int cc_insectbite = 0;
    private int cc_irregularheartbeat = 0;
    private int cc_jawpain = 0;
    private int cc_jointswelling = 0;
    private int cc_kneeinjury = 0;
    private int cc_kneepain = 0;
    private int cc_laceration = 0;
    private int cc_leginjury = 0;
    private int cc_legpain = 0;
    private int cc_legswelling = 0;
    private int cc_lethargy = 0;
    private int cc_lossofconsciousness = 0;
    private int cc_maleguproblem = 0;
    private int cc_mass = 0;
    private int cc_medicalproblem = 0;
    private int cc_medicalscreening = 0;
    private int cc_medicationproblem = 0;
    private int cc_medicationrefill = 0;
    private int cc_migraine = 0;
    private int cc_modifiedtrauma = 0;
    private int cc_motorcyclecrash = 0;
    private int cc_motorvehiclecrash = 0;
    private int cc_multiplefalls = 0;
    private int cc_nasalcongestion = 0;
    private int cc_nausea = 0;
    private int cc_nearsyncope = 0;
    private int cc_neckpain = 0;
    private int cc_neurologicproblem = 0;
    private int cc_numbness = 0;
    private int cc_oralswelling = 0;
    private int cc_otalgia = 0;
    private int cc_other = 0;
    private int cc_overdose_accidental = 0;
    private int cc_overdose_intentional = 0;
    private int cc_pain = 0;
    private int cc_palpitations = 0;
    private int cc_panicattack = 0;
    private int cc_pelvicpain = 0;
    private int cc_poisoning = 0;
    private int cc_post_opproblem = 0;
    private int cc_psychiatricevaluation = 0;
    private int cc_psychoticsymptoms = 0;
    private int cc_rapidheartrate = 0;
    private int cc_rash = 0;
    private int cc_rectalbleeding = 0;
    private int cc_rectalpain = 0;
    private int cc_respiratorydistress = 0;
    private int cc_ribinjury = 0;
    private int cc_ribpain = 0;
    private int cc_seizure_newonset = 0;
    private int cc_seizure_priorhxof = 0;
    private int cc_seizures = 0;
    private int cc_shortnessofbreath = 0;
    private int cc_shoulderinjury = 0;
    private int cc_shoulderpain = 0;
    private int cc_sicklecellpain = 0;
    private int cc_sinusproblem = 0;
    private int cc_skinirritation = 0;
    private int cc_skinproblem = 0;
    private int cc_sorethroat = 0;
    private int cc_stdcheck = 0;
    private int cc_strokealert = 0;
    private int cc_suicidal = 0;
    private int cc_suture_stapleremoval = 0;
    private int cc_swallowedforeignbody = 0;
    private int cc_syncope = 0;
    private int cc_tachycardia = 0;
    private int cc_testiclepain = 0;
    private int cc_thumbinjury = 0;
    private int cc_tickremoval = 0;
    private int cc_toeinjury = 0;
    private int cc_toepain = 0;
    private int cc_trauma = 0;
    private int cc_unresponsive = 0;
    private int cc_uri = 0;
    private int cc_urinaryfrequency = 0;
    private int cc_urinaryretention = 0;
    private int cc_urinarytractinfection = 0;
    private int cc_vaginalbleeding = 0;
    private int cc_vaginaldischarge = 0;
    private int cc_vaginalpain = 0;
    private int cc_weakness = 0;
    private int cc_wheezing = 0;
    private int cc_withdrawal_alcohol = 0;
    private int cc_woundcheck = 0;
    private int cc_woundinfection = 0;
    private int cc_woundre_evaluation = 0;
    private int cc_wristinjury = 0;
    private int cc_wristpain = 0;
}
