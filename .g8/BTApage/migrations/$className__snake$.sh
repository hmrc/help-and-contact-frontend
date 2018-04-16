#!/bin/bash

echo "Applying migration $className;format="snake"$"

echo "Adding routes to conf/app.routes"
echo "" >> ../conf/app.routes
echo "GET        /$className;format="decap"$                       controllers.$className$Controller.onPageLoad()" >> ../conf/app.routes

echo "Adding messages to conf.messages (English)"
echo "" >> ../conf/messages.en
echo "#######################################################" >> ../conf/messages.en
echo "##  $className$" >> ../conf/messages.en
echo "#######################################################" >> ../conf/messages.en
echo "$className;format="decap"$.title = $className;format="decap"$" >> ../conf/messages.en
echo "$className;format="decap"$.heading = $className;format="decap"$" >> ../conf/messages.en

echo "Adding messages to conf.messages (Welsh)"
echo "" >> ../conf/messages.cy
echo "#######################################################" >> ../conf/messages.cy
echo "##  $className$" >> ../conf/messages.cy
echo "#######################################################" >> ../conf/messages.cy
echo "$className;format="decap"$.title = WELSH NEEDED HERE" >> ../conf/messages.cy
echo "$className;format="decap"$.heading = WELSH NEEDED HERE" >> ../conf/messages.cy

echo "Moving test files from generated-test/ to test/"
rsync -avm --include='*.scala' -f 'hide,! */' ../generated-test/ ../test/
rm -rf ../generated-test/

echo "Migration $className;format="snake"$ completed"
