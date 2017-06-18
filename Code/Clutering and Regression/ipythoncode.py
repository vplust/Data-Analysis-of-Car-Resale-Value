import numpy as np
import matplotlib
from matplotlib import pyplot as plt
from matplotlib import style
style.use('ggplot')
import csv 
from statistics import mean 
import glob2 
from sklearn.cluster import KMeans
manufacturers = [] 
new_list = []
with open('car_list.csv', 'rt') as f:
    reader = csv.reader(f)
    for row in reader:
        manufacturers.append(row[0])
manufacturers  = manufacturers[1:]
manufacturers_len = len(set(manufacturers))
print(manufacturers_len)
set_manunfacturers = set(manufacturers)
set_manunfacturers


cars  = []
with open('car_list.csv', 'rt') as f:
    reader = csv.reader(f)
    for row in reader:
        cars.append(row[1])
cars = cars[1:]
import re
count = 0
new_list = []

for m in set_manunfacturers:
    for car in cars:
        if str(m) in str(car):
            count = count+1
            small_list = []
            new_list.append((re.sub(str(m),"",str(car),count=1)).lower())
car_list = []

for list in new_list:
    car_list.append((' '.join(list.split(' ')[1:-1])).capitalize())
path = '/home/saksham/Desktop/majorproject/out1/*.csv'       
print(path)




for file in glob2.glob(path):
    print(file)
    for car in car_list:
        
        with open(file, 'rt') as f:
            x = []
            y = []
            reader = csv.reader(f)
            for row in reader:
                if row[1] == car:
                    c = car
                    x.append(int(2017 - int(row[3])))
                    y.append(int(row[2]))
            
            if len(x)>0:
                print('running')
                nparray = np.empty(shape=[0, 2])
                xs = np.empty(shape=[0,1], dtype=np.float64)
                ys = np.empty(shape=[0,1], dtype=np.float64)
                no_of_clusters = len(set(x))
                for (a, b) in zip(x,y):
                    nparray = np.append(nparray, [[a,b]], axis=0)
                
                kmeans = KMeans(n_clusters=no_of_clusters)
                kmeans.fit(nparray)
                centroids = kmeans.cluster_centers_
                labels = kmeans.labels_
                if no_of_clusters == 7:
                    colors = ["g.", "r.","c.","b.","y.", "w.", "k."]
                elif  no_of_clusters == 6:
                    colors = ["g.", "r.","c.","b.","y.", "w."]
                elif no_of_clusters == 5:
                    colors = ["g.", "r.","c.","b.","y."]
                elif no_of_clusters == 4:
                    colors = ["g.", "r.","c.","b."]
                elif no_of_clusters == 3:
                    colors = ["g.", "r.","c."]
                elif no_of_clusters == 2:
                    colors = ["g.", "r."]
                elif no_of_clusters == 1:
                    colors = ["g."]
                print(car)
                for i in range(len(nparray)):
                    
                    plt.plot(nparray[i][0], nparray[i][1], colors[labels[i]], markersize=10)
                plt.scatter(centroids[:, 0], centroids[:, 1], marker="x", s=150, linewidths=5, zorder=10)
                plt.xlabel("Year")
                plt.ylabel("Price")
                fig1 = plt.gcf()
                plt.draw()
                fig1.savefig('clusteredimage/' + str(car) + '.png')

        