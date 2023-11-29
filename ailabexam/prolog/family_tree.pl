% Facts: Define the relationships in the family tree.

male(john).
male(bob).
male(jim).
male(mark).
female(jane).
female(susan).
female(linda).
female(ann).

parent(john, bob).
parent(john, jim).
parent(jane, bob).
parent(jane, jim).

parent(bob, mark).
parent(susan, mark).

parent(mark, linda).
parent(mark, ann).

% Rules: Define additional relationships.

father(X, Y) :- male(X), parent(X, Y).
mother(X, Y) :- female(X), parent(X, Y).
grandparent(X, Z) :- parent(X, Y), parent(Y, Z).

% Queries: Ask questions about the family relationships.


