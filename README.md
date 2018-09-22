# Presto

#problem & solutions


 retrieve images from network layer
 #solutions:
      •	Have a buffer, which can store a certain fixed number of images retrieved from the network layer.
      • Reason: A fixed size buffer provides a storage for retrieved data. 
                If the list is scrolling in the middle of the range of start and end of the buffer, 
                then use the data from the buffer. It also avoids memory overflow.
                
                
data loading time
#solutions:
    •	Based on the list size of each screen, each retrieval should get a little more images then it actual need of a screen.
    •	Remember the starting index and ending index, especially reaches the end of the available data.
    • Reason: can minimize the data loading time with only the data needed for the current screen
     
 
 
#Trade-offs you might have made
    scroll top/bottom detection,
    image adapter reset everytime loading images
    


 
