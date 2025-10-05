# 💳 Credit Simulator

A Java console application for simulating vehicle loan calculations.  
Users can input loan data interactively through the terminal or by providing a JSON/txt file.  
This project demonstrates structured Java design, proper error handling, and cross-platform executability (Windows, MacOS, and Linux via Docker).

---

## 📁 Project Structure

```
credit_simulator/
├── src/
│ ├── main/java/com/axelnathanael/cresim/
│ │ ├── Application.java # Main class (entry point)
│ │ ├── model/ # VehicleModel, LoanModel, Enums
│ │ ├── service/ # LoanCalculatorService
│ │ ├── view/ # MenuView (CLI handling)
│ │ ├── helper/ # Constants & utilities
│ │ └── web/ # WebService (for mock API)
│ └── test/java/... # Unit tests
├── bin/
│ └── credit_simulator # Linux/Mac launcher script
├── target/
│ └── credit_simulator-1.0-SNAPSHOT.jar
├── Dockerfile
├── pom.xml
├── README.md
└── file_inputs.json # Example file input
```


🧭 Run the Application

🟦 Option 1 — Run Interactively (via Terminal)

🔹 On Windows:
```
cd "C:\path\to\credit_simulator"
java -jar target\credit_simulator-1.0-SNAPSHOT.jar
```

🔹 On Mac/Linux:
```
java -jar target/credit_simulator-1.0-SNAPSHOT.jar
```
atau 
```
path\credit_simulator.bat
```


🟩 Option 2 — Run Using File Input

You can load data directly from a JSON/txt file.
```
java -jar target/credit_simulator-1.0-SNAPSHOT.jar "credit_simulator file_inputs.txt"
```
atau
```
pat\credit_simulator.bat "credit_simulator file_inputs.txt"
```

file structure example

```json
{
  "data": [
    {
      "vehicleType": "Mobil",
      "vehicleCondition": "Baru",
      "loanAmount": 100000000,
      "downPayment": 25000000,
      "rate": 8.0,
      "tenure": 3
    },
    {
      "vehicleType": "Motor",
      "vehicleCondition": "Bekas",
      "loanAmount": 50000000,
      "downPayment": 17500000,
      "rate": 9.0,
      "tenure": 3
    }
  ]
}
```

🟨 Option 3 — Run via Docker

1️⃣ Build the Docker image:
```
docker build -t credit-simulator .
```

2️⃣ Run Interactively:
```
docker run -it credit-simulator
```

3️⃣ Run with File Input:
```
docker run -it -v $(pwd)/file_inputs.json:/app/file_inputs.json credit-simulator file_inputs.json
```


👨‍💻 Author

Axel Nathanael
Full Stack Developer — Java | Spring | Kafka | Android
📍 Jakarta, Indonesia
📧 axelnathanaelaja@gmail.com