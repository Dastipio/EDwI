using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Net;
using System.Text.RegularExpressions;


namespace WindowsFormsApplication1
{
    public partial class Form1 : Form
    {


        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            
        }

        //wyszukiwanie dla 1 słowa
        private static int GetWordCount(string fileName, string word)
        {
            string content = File.ReadAllText(fileName);
            string[] words = content.Split(new char[] { '.', '.', ' ', '?', '\n', '\r' },
                         StringSplitOptions.RemoveEmptyEntries);
            return words.Count(q => q == word);
        }


        //cwiczenie 1 
        public void DownloadHTMLcode(string documentUrl)
        {
           //utworzenie clienta i pobranie kodu html z podanego linka
            WebClient client = new WebClient();
            string htmlCode = client.DownloadString(documentUrl); 

            // zmniejszenie pobranego zrodla strony do malych znakow
            string lower = htmlCode.ToLower();

            //wyfiltrowanie tekstu za pomocą regexa - usuniecie wszystkiego oprocz zawartosci pattern
            string pattern = "[^0-9a-zżźąęźćłóńśĄŻŹĆŃÓŁŚA-Z]";
            string nohtml = Regex.Replace(lower, pattern, " ");

            //usuniecie nadmiernych spacji z pliku - zamiana wielu na 1 
            nohtml = System.Text.RegularExpressions.Regex.Replace(nohtml, @"\s+", " ");
          
            //Console.WriteLine(nohtml);

            //zapis do pliku
            StreamWriter writer = new StreamWriter("nohtml.txt");
            writer.Write(nohtml);
            writer.Close();
        }



        //Rodzielenie słów na słownik
        public void SplitWordsIntoDictionary(string fileName)
        {
            //otwarcie pliku
            string content = File.ReadAllText(fileName);
            Dictionary<string, int> dictionary = new Dictionary<string, int>();


            foreach (string key in content.Split(' '))
            {
                int value = 0;
                foreach (string word in content.Split(' '))
                {
                    if (key.Equals(word))
                    {
                        value++;
                    }
                }
                if (dictionary.ContainsKey(key))
                {
                    dictionary[key] = value;
                }
                else
                {
                    dictionary.Add(key, value);
                }
        
            }
            var sortedDict = from entry in dictionary orderby entry.Value descending select entry;
            string s = string.Join("\n", sortedDict.Select(x => x.Key + "=" + x.Value).ToArray());
            Console.Write(s);
        }


        private void button1_Click(object sender, EventArgs e)
        {
            DownloadHTMLcode("http://pduch.kis.p.lodz.pl/");
            SplitWordsIntoDictionary("nohtml.txt");

        }

        private void button2_Click_1(object sender, EventArgs e)
        {
     
        }
    }
   
}
