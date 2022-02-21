## vehiclemiles
Vehicle Miles Traveled REST API

###Google Drive docs
- Video explicativo de la API: https://drive.google.com/file/d/1G4l6_JatjJk5oX4b7U1ok8pgTAb_Nqed/view?usp=sharing
- Archivo .cvs utilizado durante las pruebas locales: https://drive.google.com/file/d/1uGKcwRZg3_1HCn_LGg9_g7eCFrZlnMsg/view?usp=sharing

Acciones
- Ejecutar app con maven: <b>mvn spring-boot:run</b>
- Generar imagen docker: <b>mvn clean install</b>
- Subir imagen docker a gcloud: <b>gcloud docker -- push vehiclemiles:latest</b>

Desplegar en AWS:
- Desplegar el deployment: <b>envsubst < k8s/deployment.yml | kubectl apply -f -</b>
- Desplegar el service: <b>envsubst < k8s/service.yml | kubectl apply -f -</b>
- Buscar que el pod está en ejecución: <b>kubectl get pods | grep vehicle</b>
- Ejecutar port-forward al proyecto en el cloud: <b>kubectl port-forward vehiclemiles-5f88cccb65-7455h 8081:8080</b>

Heroku
- base-url: https://vehicle-miles-traveled.herokuapp.com/
- swagger: https://vehicle-miles-traveled.herokuapp.com/swagger-ui.html

## Notes:
- La Documentación de la API está publicada bajo la url http://localhost:8080/swagger-ui.html
- La applicación puede bien usar postgres como repositorio de datos o bien H2.
- Modificar el application.properties para seleccionar cual base de datos utilizar.
- Por defecto application.properties está configurado para H2, si desea ir a postgres, debe descomentar las properties correspondiente y crear la bd vehicle, y finalmente comentar la configuración de H2.