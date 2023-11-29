% Facts about symptoms and corresponding conditions
symptom(john, fever).
symptom(john, cough).
symptom(john, fatigue).
symptom(jane, headache).
symptom(jane, fever).
symptom(jane, nausea).
symptom(jack, cough).
symptom(jack, fatigue).
symptom(jack, shortness_of_breath).

% Facts about conditions and their characteristics
condition(fever, high_temperature).
condition(cough, persistent).
condition(headache, severe).
condition(fatigue, constant_tiredness).
condition(nausea, occasional).
condition(shortness_of_breath, wheezing).

% Additional facts for complex conditions
complex_condition(influenza, [fever, cough, fatigue]).
complex_condition(migraine, [headache, nausea]).
complex_condition(pneumonia, [fever, cough, shortness_of_breath]).