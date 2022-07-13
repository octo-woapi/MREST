#!/bin/bash

#################Environement

FILE=.env



##################recherche ip
echo "recherche de l'ip..."
DOCKER_HOST=$(ip route get 1 | sed -n 's/^.*src \([0-9.]*\) .*$/\1/p')
echo " ip trouvée: ${DOCKER_HOST}"


##################gestion fichier .en du docker compose

if [ -f "$FILE" ]
then # fichier .env present

    #patching du fichier
    exists=$(grep "DOCKER_HOST" .env| head -c1 | wc -c)
    if [[ $? -eq 0 ]]
    then
        if [[ $exists -ne 0 ]]
        then # variable exists : changement de la valeur
            echo "variable already exists in file : patching"
            sed -i "s/\(^DOCKER_HOST=\).*/\1${DOCKER_HOST}/" $FILE
            echo $?
        else # variable n'existe pas : on ajoute
            echo " adding variable to env file"
            echo "DOCKER_HOST=${DOCKER_HOST}" >> $FILE
        fi
    else
        echo "erreur dans la detection de la variable dans le fichier existant .env"
    fi

else # pas de fichier d'environnement
    echo "DOCKER_HOST=${DOCKER_HOST}" > $FILE

fi

echo "fichier .env à jour :"
cat $FILE

docker compose up -d

