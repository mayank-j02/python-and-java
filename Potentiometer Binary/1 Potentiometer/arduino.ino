void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
}

void loop() {
  // put your main code here, to run repeatedly:
  int analogValue = analogRead(A2);

  byte b1 = analogValue / 256;
  byte b2 = analogValue % 256;

  //byte b[] = {b1, b2};

  Serial.write(b1);
  Serial.write(b2);

}
