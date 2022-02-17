int ledPin = 13;
int analogPin = A0;
int analogPin1 = A1;
int analogValue = 0;
int analogValue1 = 0;

void setup() {
  pinMode(ledPin, OUTPUT);
  Serial.begin(9600);
}

void loop() {
  digitalWrite(ledPin, HIGH);

  analogValue = analogRead(analogPin);  //Read analog input
  analogValue = map(analogValue, 0, 1023, 0, 255);
  //Serial.println(analogRead(analogPin));
  //Serial.write(analogValue);  //write as byte, to USB

  analogValue1 = analogRead(analogPin1);
  analogValue1 = map(analogValue1, 0, 1023, 0, 255);
  //Serial.write(analogValue1);
  //Serial.println(analogRead(analogPin1));
  
  int arr[2]={analogValue, analogValue1};
  //Serial.println(arr[0]);
  //Serial.println(arr[1]);

  Serial.write((uint8_t*)arr, 2);
  
  digitalWrite(ledPin, LOW);
  delay(100);
}
