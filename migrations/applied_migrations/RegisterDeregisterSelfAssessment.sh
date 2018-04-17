#!/bin/bash

echo "Applying migration RegisterDeregisterSelfAssessment"

echo "Adding routes to conf/app.routes"
echo "" >> ../conf/app.routes
echo "GET        /registerDeregisterSelfAssessment                       controllers.RegisterDeregisterSelfAssessmentController.onPageLoad()" >> ../conf/app.routes

echo "Adding messages to conf.messages (English)"
echo "" >> ../conf/messages.en
echo "#######################################################" >> ../conf/messages.en
echo "##  RegisterDeregisterSelfAssessment" >> ../conf/messages.en
echo "#######################################################" >> ../conf/messages.en
echo "registerDeregisterSelfAssessment.title = registerDeregisterSelfAssessment" >> ../conf/messages.en
echo "registerDeregisterSelfAssessment.heading = registerDeregisterSelfAssessment" >> ../conf/messages.en

echo "Adding messages to conf.messages (Welsh)"
echo "" >> ../conf/messages.cy
echo "#######################################################" >> ../conf/messages.cy
echo "##  RegisterDeregisterSelfAssessment" >> ../conf/messages.cy
echo "#######################################################" >> ../conf/messages.cy
echo "registerDeregisterSelfAssessment.title = WELSH NEEDED HERE" >> ../conf/messages.cy
echo "registerDeregisterSelfAssessment.heading = WELSH NEEDED HERE" >> ../conf/messages.cy

echo "Moving test files from generated-test/ to test/"
rsync -avm --include='*.scala' -f 'hide,! */' ../generated-test/ ../test/
rm -rf ../generated-test/

echo "Migration RegisterDeregisterSelfAssessment completed"
