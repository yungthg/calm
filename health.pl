:- initialization(start).
symptom(fever).
symptom(cough).
symptom(rash).
symptom(headache).

disease(flu) :- symptom(fever), symptom(cough).
disease(measles) :- symptom(fever), symptom(rash).
disease(meningitis) :- symptom(fever), symptom(headache).

% Define inference mechanism
diagnose(Disease) :- disease(Disease), !.

% User interface predicates
start :-
    write('Welcome to the Medical Expert System'), nl,
    write('Please enter your symptoms separated by commas: '),
    read(Symptoms),
    diagnose_disease(Symptoms).

diagnose_disease(Symptoms) :-
    diagnose(Disease),
    write('Based on the symptoms provided, the most likely disease is: '), write(Disease), nl.