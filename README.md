## PD Library rental service

## Build and run by executing following steps manually

### Production

Production is running in GCP.

1. #### Follow [usual steps](https://cloud.google.com/compute/docs/instances/connecting-to-instance) for connecting to a VM running in GCP
   Connect via SSH to a **rbc-library-rental-vm**.
2. #### Go to a folder where backend is:
    ``` 
    cd /home/pd-library/rbc-library-rental
    ``` 
3. #### This is a Git repository. Pull most recent version from main:
    ```  
   sudo git checkout main
   sudo git pull
    ``` 
4. #### Maven is already installed, so you can build new jar with maven:

   **!!! Important: You need to switch to a root user !!!**

   _Why? Otherwise it will download entire .m2 repository to a home folder of a user who made SSH connection._
    ```  
    sudo su
   ../apache-maven-3.8.5/bin/mvn package -P prod
    ``` 
5. #### Restart rbc-library-rental-vm to run a new jar

   **rbc-library-rental-vm** is configured with startup-script. Startup script already contains instructions for running
   the jar:

   **Startup-script content:**

    ```
    #! /bin/bash
    SPRING_DATASOURCE_PASSWORD=<REAL_PASS_HERE> java -jar /home/pd-library/rbc-library-rental/target/rbc-library-rental-0.0.1-SNAPSHOT.jar
    ```
