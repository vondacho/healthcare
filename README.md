# Healthcare

This application simulates the future patients' state, based on their current state  and a list of medicines they take.

Patients can have one of these states:

* F: Fever
* H: Healthy
* D: Diabetes
* T: Tuberculosis
* X: Dead

Drugs are provided to all patients. It is not possible to target a
specific patient. This is the list of available drugs:

* As: Aspirin
* An: Antibiotic
* I: Insulin
* P: Paracetamol

## Usage

### Input

#### Parameter 1

List of patients' health status codes, separated by a comma. e.g. “D,F,F” means we have 3
patients, one with diabetes and two with fever.

#### Parameter 2

List of drugs codes, separated by a comma, e.g. “As,I” means patients will be treated with
Aspirin and Insulin.

### Examples

Diabetic patients die without insulin

`$ java -jar healthcare-fat.jar D,D`

`F:0,H:0,D:0,T:0,X:2`

Paracetamol cures fever

`$ java -jar healthcare-fat.jar F P`

`F:0,H:1,D:0,T:0,X:0`

## Technical

* Developed under Kotlin 1.3
* Built with Maven
* Usage of marcinmoskala / kotlindiscretemathtoolkit

### Build

`$ mvn clean package`
