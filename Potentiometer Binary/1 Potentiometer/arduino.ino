void setup() {
  Serial.begin(9600);
}

void loop() {
  int analogValue = analogRead(A2);

  byte b1 = analogValue / 256;
  byte b2 = analogValue % 256;

  byte b[] = {b1, b2};

  Serial.write(b, 2);
}
