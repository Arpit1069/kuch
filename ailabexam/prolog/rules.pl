% Rules for diagnosing conditions based on symptoms
diagnose_patient(Patient, Condition) :-
    symptom(Patient, Symptom),
    condition(Condition, Characteristic),
    has_characteristic(Symptom, Characteristic).

% Rules for diagnosing complex conditions
diagnose_complex_condition(Patient, ComplexCondition) :-
    complex_condition(ComplexCondition, Symptoms),
    forall(member(Symptom, Symptoms), symptom(Patient, Symptom)).

% Rule to check if a symptom has a specific characteristic
has_characteristic(Symptom, Characteristic) :-
    symptom_has_characteristic(Symptom, Characteristic).

% Helper rules for symptom characteristics
symptom_has_characteristic(fever, high_temperature).
symptom_has_characteristic(cough, persistent).
symptom_has_characteristic(headache, severe).
symptom_has_characteristic(fatigue, constant_tiredness).
symptom_has_characteristic(nausea, occasional).
symptom_has_characteristic(shortness_of_breath, wheezing).