'''
A unit test of the Directory application. Demonstrates use of the Adobe Flex/Flash
AMF protocol handler in PushToTest TestMaker.

For details see http://www.pushtotest.com

(c) 2011 PushToTest. All rights reserved.
'''

import sys

'''from com.pushtotest.tool.protocolhandler.AMF import AMFProtocol
from com.pushtotest.tool import PTTStepListener'''
from com.appvance.enterprise.tool import APCStepListener
from com.appvance.enterprise.tool.protocolhandler.AMF import AMFProtocol

class AMFTest:
    def __init__(self):
    	pass

    def setUp(self):
        pass

    def runTest(self,person):
        ''' 
        Add a new person to the directory and check that the person is 
        actually in the directory. Receive the person data from a 
        provided hashmap from a TestMaker Data Production Library (DPL.) 
        '''
        self.AMF = AMFProtocol()

        APCStepListener.startStep( "setUp" );
        
        self.AMF.setup("http://localhost:8080/openamf/gateway")
        self.AMF.setVerbose(1)

        APCStepListener.endStep()
        
        APCStepListener.startStep( "addPerson" )

        data = [person]
        self.AMF.invoke("org.openamf.examples.Directory.addPerson",data)

        APCStepListener.endStep()
        
        APCStepListener.startStep( "getPeople" )
        
        name = person.get("firstName")
        data[0] = name
        result = self.AMF.invoke("org.openamf.examples.Directory.getPeople",data)

        APCStepListener.endStep()
        
        APCStepListener.startStep( "tearDown" )

        resultperson = result[0]
        print resultperson

        self.AMF.teardown()

        APCStepListener.endStep()

    def tearDown(self):
        pass

def getAMFTest():
    ''' Needed by TestMaker to instantiate this test object '''
    return AMFTest()

