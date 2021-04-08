package main

import (
	"fmt"
	"sync"
)

type Praporshchik struct{
    name string
    in, out chan string
}

func (p Praporshchik) run(itemsCounter *int, items []string, wg *sync.WaitGroup){
    for i:= 0; i < len(items); i++{
        var item string
        if p.in != nil{
            item = <- p.in
        } else {
            (*itemsCounter)--
            item = items[*itemsCounter]
        }
        fmt.Printf("%s got %s\n", p.name, item)
        if p.out != nil{
            p.out <- item
        }
    }
    wg.Done()
}

func genItems(it []string) []string{
    it = append(it, "wallpaper")
    it = append(it, "weapon")
    it = append(it, "chair")
    it = append(it, "beer")
    it = append(it, "bike")
    it = append(it, "bed")
    it = append(it, "meat")
    it = append(it, "suit")
    it = append(it, "pan")
    it = append(it, "helmet")
    it = append(it, "joggers")
    return it
}

func genThieves(th []Praporshchik) []Praporshchik{
    stockToVan := make(chan string)
    vanToCount := make(chan string)
    var Ivanov, Petrov, Sydorov Praporshchik
    Ivanov.name = "Ivanov"
    Ivanov.out = stockToVan
    th = append(th, Ivanov)
    Petrov.name = "Petrov"
    Petrov.in = stockToVan
    Petrov.out = vanToCount
    th = append(th, Petrov)
    Sydorov.name = "Sydorov"
    Sydorov.in = vanToCount
    th = append(th, Sydorov)
    return th
}

func main() {
    thieve := make([]Praporshchik, 0)
    stockItem := make([]string, 0)
    stockItem = genItems(stockItem)
    thieve = genThieves(thieve)
    itemsCounter := len(stockItem)
    var wg sync.WaitGroup
    wg.Add(len(thieve))
    for _, p := range thieve{
        go p.run(&itemsCounter, stockItem, &wg)
    }
    wg.Wait()
}
