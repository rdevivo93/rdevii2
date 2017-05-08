Authors:
Ray DeVivo
Arnav Dahal

Created and compiled on eclipse. 

Make sure the blocking number entered is valid and less than the length of your prime prime number.
Do not change it after used. You will get odd results.

Prime numbers are in source directory and should be named "primeNumbers.rsc"
To check if prime, i used ideas from this and changed it to work for me: http://www.mkyong.com/java/how-to-determine-a-prime-number-in-java/

Currently the e value is the first value that works after 4.

While using. After key creation the public key file will be named pubkey and the private key file will be named
prikey.
The name of the message to be blocked is up to the user.
When using the block file option, the blocked file will be output to a file named rsaBlocked in the same directory.
Then when encrypting use pubkey, the output file is called encrypted. When choosing decrypt using prikey.
you will choose encrypted as the file.Make sure to remember your blocking size.
After it is decrypted, it will still be in the same file. Use unblock on encrypted and the text will be ouput to
rsaMessage.txt



