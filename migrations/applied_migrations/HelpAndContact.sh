#!/bin/bash

echo "Applying migration HelpAndContact"

echo "Adding routes to conf/app.routes"
echo "" >> ../conf/app.routes
echo "GET        /helpAndContact                       controllers.HelpAndContactController.onPageLoad()" >> ../conf/app.routes

echo "Adding messages to conf.messages (English)"
echo "" >> ../conf/messages.en
echo "#######################################################" >> ../conf/messages.en
echo "##  HelpAndContact" >> ../conf/messages.en
echo "#######################################################" >> ../conf/messages.en
echo "helpAndContact.title = helpAndContact" >> ../conf/messages.en
echo "helpAndContact.heading = helpAndContact" >> ../conf/messages.en

echo "Adding messages to conf.messages (Welsh)"
echo "" >> ../conf/messages.cy
echo "#######################################################" >> ../conf/messages.cy
echo "##  HelpAndContact" >> ../conf/messages.cy
echo "#######################################################" >> ../conf/messages.cy
echo "helpAndContact.title = WELSH NEEDED HERE" >> ../conf/messages.cy
echo "helpAndContact.heading = WELSH NEEDED HERE" >> ../conf/messages.cy

echo "Moving test files from generated-test/ to test/"
rsync -avm --include='*.scala' -f 'hide,! */' ../generated-test/ ../test/
rm -rf ../generated-test/

echo "Migration HelpAndContact completed"
