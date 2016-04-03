using System;
using System.Collections.Generic;
using System.Text;
using System.Net;

namespace CheckURL
{
    class CheckURL
    {
        static int Main(string[] args)
        {
            String sURL = null, sAssertText = null;
            // Check for args
            for (int i = 0; i < args.Length; i++)
            {
                switch (args[i].ToLower())
                {
                    case "-url":
                        i++;
                        if (i == args.Length)
                        {
                            Console.WriteLine("Error: Missing URL");
                            return 1000;
                        }
                        else
                        {
                            if (args[i].StartsWith("-"))
                            {
                                Console.WriteLine("Error: Missing URL");
                                return 1000;
                            }
                            sURL = args[i];
                            Console.WriteLine("URL: "+sURL);
                        }
                        break;
                    case "-asserttext":
                        i++;
                        if (i == args.Length)
                        {
                            Console.WriteLine("Error: Missing Assert Text");
                            return 1001;
                        }
                        else
                        {
                            if (args[i].StartsWith("-"))
                            {
                                Console.WriteLine("Error: Missing Assert Text");
                                return 1001;
                            }
                            sAssertText = args[i];
                            Console.WriteLine("Text: " + sAssertText);
                        }
                        break;
                    default:
                        Console.WriteLine("Invalid parameter. ");
                        return 1009;
                }
            }

            if (sURL == null){
                Console.WriteLine("Error: Missing URL");
                return 1000;
            }
            if (sAssertText == null)
            {
                Console.WriteLine("Error: Missing Assert Text");
                return 1001;
            }
            Console.WriteLine("Loading Page...");
            HttpWebRequest wRequest = (HttpWebRequest)WebRequest.Create(sURL);
            wRequest.AllowAutoRedirect = false;
            wRequest.MaximumAutomaticRedirections = 1;

            Console.WriteLine("Reading Response...");

            WebResponse wResponse = wRequest.GetResponse();
            System.IO.Stream rStream = wResponse.GetResponseStream();
            System.IO.StreamReader sReader = new System.IO.StreamReader(rStream);

            Console.WriteLine("Consuming Response...");
            string sResponse = sReader.ReadToEnd();

            Console.WriteLine("Response..." + sResponse);
            // Check the Text Present
            if (sResponse.ToLower().IndexOf(sAssertText.ToLower()) < 0)
            {
                Console.WriteLine("Error: Text not Found " + sAssertText);
                return 1005;
            }
            else
            {
                Console.WriteLine("Assert: Text " + sAssertText + " Found ");
            }
            return 0;

        }
    }
}
