#include <SoftwareSerial.h>
SoftwareSerial mySerial(10, 11); // RX, TX

int calibrationTime = 10;        
long unsigned int  lowIn;
long unsigned int pause = 1000;

boolean  lockLow = true;
boolean takeLowTime;  

int  pirPin = 2;    
int  ledPin = 13;

void setup(){
  Serial.begin(9600); 
  pinMode(ledPin, OUTPUT);      // declare LED as output
  pinMode(pirPin, INPUT);     // declare sensor as input
  digitalWrite(pirPin,LOW);
  activaSensor();
}

void loop(){
  if(digitalRead(pirPin) == HIGH){
    mandaA();
  }
  if(digitalRead(pirPin) == LOW){
    mandaB();
  } 
}

void activaSensor(){

  pinMode(pirPin, INPUT);
  digitalWrite(pirPin, LOW);

  //Dar el sensor de un cierto tiempo para calibrar
  Serial.print(" calibrando sensor ");
  for(int i = 0; i < calibrationTime; i++){     
    Serial.print(".");     
    delay(1000);   
  }  
  Serial.println("SENSOR ACTIVADO");  
  delay(50); 
} 


void mandaA(){  
  digitalWrite(ledPin, HIGH);   //LED visualiza el estado de la salida del sensor
  if(lockLow){ 
    // solo paso por aqui cuando la deteccion es nueva, es decir cuando lockLow es TRUE, y solo es TRUE cuando pasa por la deteccion de NO movimiento
    lockLow = false;           
    Serial.println("movimiento");
    delay(50);
  }        
  takeLowTime = true; // Si lockLow no esta bloqueado
} 
void mandaB(){ 
  digitalWrite(ledPin, LOW);  //LED visualiza el estado de la salida del sensor

  if(takeLowTime){      // solo paso por aqui despues de haber detectado movimiento
    //en las siguientes vueltas si sigue sin haber movimiento ya no necesito guardar el timepo en el que el movimiento par´
    lowIn = millis();          // guardar el momento de la transición de alta a baja
    takeLowTime = false;       //  asegurarse de que esto sólo se hace al comienzo de una fase de baja
  }

  //Si el sensor esta LOW mas tiempo que PAUSE, es que el movimiento ha parado de verdad      
  if(!lockLow && millis() - lowIn > pause){ 
    // lockLow solo es FALSE cuando se ha detectado movimiento de nuevo, es decir solo paso por aqui cuando hay nueva deteccion       
    lockLow = true;                       
   //  Serial.print("movimiento detenido a los ");     
   //  Serial.print((millis() - pause)/1000);
   //  Serial.println(" segundos ");
    delay(50);
  }
}


void mandaAviso(String msg){
  Serial.println(msg);
}



