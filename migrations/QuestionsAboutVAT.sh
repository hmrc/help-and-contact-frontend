#!/bin/bash

echo "Applying migration QuestionsAboutVAT"

echo "Adding routes to conf/app.routes"
echo "" >> ../conf/app.routes
echo "GET        /questionsAboutVAT                       controllers.QuestionsAboutVATController.onPageLoad()" >> ../conf/app.routes

echo "Adding messages to conf.messages (English)"
echo "" >> ../conf/messages.en
echo "#######################################################" >> ../conf/messages.en
echo "##  QuestionsAboutVAT" >> ../conf/messages.en
echo "#######################################################" >> ../conf/messages.en
echo "questionsAboutVAT.title = questionsAboutVAT" >> ../conf/messages.en
echo "questionsAboutVAT.heading = questionsAboutVAT" >> ../conf/messages.en

echo "Adding messages to conf.messages (Welsh)"
echo "" >> ../conf/messages.cy
echo "#######################################################" >> ../conf/messages.cy
echo "##  QuestionsAboutVAT" >> ../conf/messages.cy
echo "#######################################################" >> ../conf/messages.cy
echo "questionsAboutVAT.title = WELSH NEEDED HERE" >> ../conf/messages.cy
echo "questionsAboutVAT.heading = WELSH NEEDED HERE" >> ../conf/messages.cy

echo "Moving test files from generated-test/ to test/"
rsync -avm --include='*.scala' -f 'hide,! */' ../generated-test/ ../test/
rm -rf ../generated-test/

echo "Migration QuestionsAboutVAT completed"
