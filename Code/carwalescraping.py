import csv
import os
import requests
from bs4 import BeautifulSoup

base_url = 'https://www.carwale.com'

manufacturer = 'marutisuziki'

detail = []

#  to find all the cars for a particular manufacturer
# soup.findAll('a', {'class': 'font18'})
car_url = 'https://www.carwale.com/marutisuzuki-cars/swift/'

car = 'swift'
def getCarDetails(car_url):
	
	print(car_url)
	car_detail = requests.get(car_url)

	soup = BeautifulSoup(car_detail.text, 'lxml')
	variants = soup.select('a.font14.text-bold')
	start = 1
	end = 1 + len(variants)
	prices_span = soup.findAll('span', {'class': 'text-bold'})[start:end]

	total = 0

	for span in prices_span:
		str_price = str(span.text).replace(' ', '').replace('\n', '').replace('\r', '')
		print(str_price[:-1])
		# if price is L
		if str_price[-1] == 'L'	:
			int_price = int(float(str_price[:-1]) * 100000)
		else:
			int_price = fint(loat(str_price[:-1]) * 10000000)

		total = total+int_price

	avg_price = int(total/len(variants))
	print(avg_price)
	detail.append(avg_price)


if __name__ == '__main__':

	getCarDetails(car_url)
	outfile = open('./car_list.csv', 'w')
	writer = csv.writer(outfile)
	writer.writerow(['COMPANY NAME', 'CAR NAME', 'AVG. ON-ROAD PRICE'])
	writer.writerow([manufacturer, car,detail ])


	