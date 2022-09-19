#include <SoftwareSerial.h>
SoftwareSerial mySerial(0, 1);

void setup()
{
  Serial.begin(115200);
  mySerial.begin(115200);
}

void loop()
{
  int pot = 1020;

  byte higherbyte = pot % 256;
  byte lowerbyte = pot / 256;

  byte arr0[11] = {5, 240, higherbyte, lowerbyte, 145, 2, 100, 3, higherbyte, lowerbyte};
  byte arr1[11] = {5, 241, higherbyte, lowerbyte, 242, 2, 153, 3, higherbyte, lowerbyte};
  byte arr2[11] = {5, 242, higherbyte, lowerbyte, 131, 2, 186, 4, higherbyte, lowerbyte};

  for(int i=0;i<3;i++){
    if(i=0){
      Serial.write(arr0, 10);
      delay(50);
    }
    if(i=1){
      Serial.write(arr1, 10);
      delay(50);
    }
    if(i=2){
      Serial.write(arr2, 10);
      delay(50);
    }
  }
  
  
  delay(500);
}
