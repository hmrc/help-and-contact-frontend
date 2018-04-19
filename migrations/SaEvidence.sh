#!/bin/bash

echo "Applying migration SaEvidence"

echo "Adding routes to conf/app.routes"
echo "" >> ../conf/app.routes
echo "GET        /saEvidence                       controllers.SaEvidenceController.onPageLoad()" >> ../conf/app.routes

echo "Adding messages to conf.messages (English)"
echo "" >> ../conf/messages.en
echo "#######################################################" >> ../conf/messages.en
echo "##  SaEvidence" >> ../conf/messages.en
echo "#######################################################" >> ../conf/messages.en
echo "saEvidence.title = saEvidence" >> ../conf/messages.en
echo "saEvidence.heading = saEvidence" >> ../conf/messages.en

echo "Adding messages to conf.messages (Welsh)"
echo "" >> ../conf/messages.cy
echo "#######################################################" >> ../conf/messages.cy
echo "##  SaEvidence" >> ../conf/messages.cy
echo "#######################################################" >> ../conf/messages.cy
echo "saEvidence.title = WELSH NEEDED HERE" >> ../conf/messages.cy
echo "saEvidence.heading = WELSH NEEDED HERE" >> ../conf/messages.cy

echo "Moving test files from generated-test/ to test/"
rsync -avm --include='*.scala' -f 'hide,! */' ../generated-test/ ../test/
rm -rf ../generated-test/

echo "Migration SaEvidence completed"
